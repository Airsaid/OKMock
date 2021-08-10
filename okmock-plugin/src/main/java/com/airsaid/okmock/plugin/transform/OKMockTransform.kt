/*
 * Copyright 2021 Airsaid. https://github.com/airsaid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.airsaid.okmock.plugin.transform

import com.airsaid.okmock.api.Mock
import com.airsaid.okmock.plugin.OKMockPlugin
import com.airsaid.okmock.plugin.constant.Constants.GET_MOCK_DATA_METHOD_DESCRIPTOR
import com.airsaid.okmock.plugin.constant.Constants.GET_MOCK_DATA_METHOD_NAME
import com.airsaid.okmock.plugin.constant.Constants.OK_MOCK_CLASS_NAME
import com.airsaid.okmock.plugin.model.FieldInfo
import com.airsaid.okmock.plugin.util.isPrimitiveType
import com.airsaid.okmock.plugin.util.toPrimitiveWrapType
import com.android.build.api.transform.TransformInvocation
import org.objectweb.asm.*
import org.objectweb.asm.Opcodes.*

/**
 * @author airsaid
 */
class OKMockTransform : AbstractTransform() {

  override fun getName() = OKMockPlugin.PLUGIN_NAME

  override fun onTransformBefore(invocation: TransformInvocation) {}

  override fun onTransform(invocation: TransformInvocation, bytecode: ByteArray): ByteArray {
    val cr = ClassReader(bytecode)
    val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES)
    val cv = OKMockClassAdapter(listOf(Mock::class.java), cw)
    cr.accept(cv, 0)
    return cw.toByteArray()
  }

  override fun onTransformAfter(invocation: TransformInvocation) {}

  private class OKMockClassAdapter(
    private val annotations: List<Class<*>>,
    cv: ClassVisitor
  ) : ClassVisitor(ASM9, cv) {

    private lateinit var owner: String

    private val fields = mutableListOf<FieldInfo>()

    private var isVisitStaticBlock = false

    override fun visit(version: Int, access: Int, name: String, signature: String?, superName: String?, interfaces: Array<out String>?) {
      this.owner = name
      super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitField(access: Int, name: String, descriptor: String, signature: String?, value: Any?): FieldVisitor {
      val fv = super.visitField(access, name, descriptor, signature, value)
      if (fv != null) {
        val fieldInfo = FieldInfo(owner, access, name, descriptor, signature, value)
        return AnnotationFieldAdapter(fields, annotations, fieldInfo, fv)
      }
      return fv
    }

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
      val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
      if ("<clinit>" == name) {
        isVisitStaticBlock = true
      }
      if (mv != null && ("<init>" == name || "<clinit>" == name)) {
        return FieldAssigneeMethodAdapter(fields, mv)
      }
      return mv
    }

    override fun visitEnd() {
      // all methods visited. if static block was not encountered
      // and it has static fields, add a new static block.
      if (!isVisitStaticBlock && hasStaticField()) {
        visitMethod(ACC_STATIC, "<clinit>", "()V", null, null).apply {
          visitCode()
          visitInsn(RETURN)
          visitMaxs(0, 0)
          visitEnd()
        }
      }
      super.visitEnd()
    }

    private fun hasStaticField(): Boolean {
      return fields.any { (it.access and ACC_STATIC) != 0 }
    }

    private class AnnotationFieldAdapter(
      private val results: MutableList<FieldInfo>,
      private val annotations: List<Class<*>>,
      private val field: FieldInfo,
      fv: FieldVisitor
    ) : FieldVisitor(ASM9, fv) {
      override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        annotations.forEach { annotation ->
          if (Type.getDescriptor(annotation) == descriptor) {
            results.add(field)
          }
        }
        return super.visitAnnotation(descriptor, visible)
      }
    }

    private class FieldAssigneeMethodAdapter(
      private val fields: MutableList<FieldInfo>,
      mv: MethodVisitor
    ) : MethodVisitor(ASM9, mv) {
      override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, descriptor: String?, isInterface: Boolean) {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
        // Add field's assignment in the construction method and static block
        if ((opcode == INVOKESPECIAL && "<init>" == name) || (opcode == RETURN && "<clinit>" == name)) {
          assignFields()
        }
      }

      private fun assignFields() {
        fields.forEach { fieldInfo ->
          val isStaticField = (fieldInfo.access and ACC_STATIC) != 0
          val fieldType = Type.getType(fieldInfo.descriptor)
          val referenceDescriptor = if (fieldType.isPrimitiveType())
            fieldType.toPrimitiveWrapType().descriptor else fieldInfo.descriptor
          val referenceType = Type.getType(referenceDescriptor)

          if (!isStaticField) {
            mv.visitVarInsn(ALOAD, 0)
          }
          mv.visitLdcInsn(getMethodParams(fieldInfo))
          mv.visitMethodInsn(
            INVOKESTATIC, OK_MOCK_CLASS_NAME,
            GET_MOCK_DATA_METHOD_NAME, GET_MOCK_DATA_METHOD_DESCRIPTOR, false
          )
          mv.visitTypeInsn(CHECKCAST, referenceType.internalName)
          if (fieldType.isPrimitiveType()) {
            mv.visitMethodInsn(
              INVOKEVIRTUAL, referenceType.internalName,
              "${fieldType.className}Value", Type.getMethodDescriptor(fieldType), false
            )
          }
          mv.visitFieldInsn(if (isStaticField) PUTSTATIC else PUTFIELD, fieldInfo.owner, fieldInfo.name, fieldInfo.descriptor)
        }
      }

      private fun getMethodParams(fieldInfo: FieldInfo): String {
        val signature = fieldInfo.signature
        return if (signature != null && signature.isNotEmpty())
          signature else fieldInfo.descriptor
      }
    }
  }
}