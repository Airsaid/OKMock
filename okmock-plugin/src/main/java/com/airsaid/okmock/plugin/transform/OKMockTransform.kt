package com.airsaid.okmock.plugin.transform

import android.databinding.tool.ext.capitalizeUS
import com.airsaid.okmock.api.Mock
import com.airsaid.okmock.plugin.OKMockPlugin
import com.airsaid.okmock.plugin.constant.Constants.OK_MOCK_CLASS_NAME
import com.airsaid.okmock.plugin.model.FieldInfo
import com.airsaid.okmock.plugin.util.*
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
      if (mv != null && name.equals("<init>")) {
        return FieldAssigneeMethodAdapter(fields, mv)
      }
      return mv
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
        if (opcode == INVOKESPECIAL && name.equals("<init>")) {
          assignFields()
        }
      }

      private fun assignFields() {
        fields.forEach { fieldInfo ->
          val fieldDescriptor = fieldInfo.descriptor
          val fieldType = Type.getType(fieldDescriptor)
          println("fieldType: $fieldType")
          if (fieldType.isPrimitiveType()) {
            assignPrimitiveType(mv, fieldInfo, fieldType)
          } else if (fieldType.isPrimitiveWrapType()) {
            assignPrimitiveWrapType(mv, fieldInfo, fieldType)
          } else if (fieldType.isStringType()) {
            assignStringType(mv, fieldInfo, fieldType)
          } else {
            assignPojoType(mv, fieldInfo, fieldType)
          }
        }
      }

      private fun assignPrimitiveType(mv: MethodVisitor, fieldInfo: FieldInfo, fieldType: Type) {
        mv.visitVarInsn(ALOAD, 0)
        val methodName = "get${fieldType.className.capitalizeUS()}"
        val methodDescriptor = Type.getMethodDescriptor(fieldType)
        mv.visitMethodInsn(INVOKESTATIC, OK_MOCK_CLASS_NAME, methodName, methodDescriptor, false)
        mv.visitFieldInsn(PUTFIELD, fieldInfo.owner, fieldInfo.name, fieldType.descriptor)
      }

      private fun assignPrimitiveWrapType(mv: MethodVisitor, fieldInfo: FieldInfo, fieldType: Type) {
        mv.visitVarInsn(ALOAD, 0)
        val methodName = "get${fieldType.toPrimitiveType().className.capitalizeUS()}"
        val methodDescriptor = Type.getMethodDescriptor(fieldType.toPrimitiveType())
        val valueOfMethodDesc = Type.getMethodDescriptor(fieldType, fieldType.toPrimitiveType())
        mv.visitMethodInsn(INVOKESTATIC, OK_MOCK_CLASS_NAME, methodName, methodDescriptor, false)
        mv.visitMethodInsn(INVOKESTATIC, fieldType.internalName, "valueOf", valueOfMethodDesc, false)
        mv.visitFieldInsn(PUTFIELD, fieldInfo.owner, fieldInfo.name, fieldType.descriptor)
      }

      private fun assignStringType(mv: MethodVisitor, fieldInfo: FieldInfo, fieldType: Type) {
        mv.visitVarInsn(ALOAD, 0)
        val methodDescriptor = Type.getMethodDescriptor(fieldType)
        mv.visitMethodInsn(INVOKESTATIC, OK_MOCK_CLASS_NAME, "getString", methodDescriptor, false)
        mv.visitFieldInsn(PUTFIELD, fieldInfo.owner, fieldInfo.name, fieldType.descriptor)
      }

      private fun assignPojoType(mv: MethodVisitor, fieldInfo: FieldInfo, fieldType: Type) {
        mv.visitVarInsn(ALOAD, 0)
        mv.visitLdcInsn(fieldType)
        mv.visitMethodInsn(INVOKESTATIC, OK_MOCK_CLASS_NAME, "getPojo", "(Ljava/lang/Class;)Ljava/lang/Object;", false)
        mv.visitTypeInsn(CHECKCAST, fieldType.internalName)
        mv.visitFieldInsn(PUTFIELD, fieldInfo.owner, fieldInfo.name, fieldType.descriptor)
      }
    }
  }
}