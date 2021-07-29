package com.airsaid.okmock.plugin.transform

import com.airsaid.okmock.plugin.model.FieldInfo
import com.android.build.api.transform.TransformInvocation
import org.objectweb.asm.*

/**
 * @author airsaid
 */
class FieldAssigneeTransform(fields: List<FieldInfo>, className: String) : AbsTransformHandler() {

  private val assignments = hashMapOf<FieldInfo, Pair<String, String>>()

  init {
    fields.forEach { assignments[it] = className to it.getGetterMethodName() }
  }

  override fun onTransform(invocation: TransformInvocation, bytecode: ByteArray): ByteArray {
    if (assignments.isEmpty()) return bytecode

    val cr = ClassReader(bytecode)
    val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES)
    val cv = FieldAssigneeClassAdapter(assignments, cw)
    cr.accept(cv, 0)
    return cw.toByteArray()
  }

  private class FieldAssigneeClassAdapter(
    private val assignments: Map<FieldInfo, Pair<String, String>>,
    cv: ClassVisitor
  ) : ClassVisitor(Opcodes.ASM9, cv) {

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
      val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
      if (mv != null && name.equals("<init>")) {
        return FieldAssigneeMethodAdapter(assignments, mv)
      }
      return mv
    }

    private class FieldAssigneeMethodAdapter(
      private val assignments: Map<FieldInfo, Pair<String, String>>,
      mv: MethodVisitor
    ) : MethodVisitor(Opcodes.ASM9, mv) {
      override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, descriptor: String?, isInterface: Boolean) {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
        if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
          assignFields()
        }
      }

      private fun assignFields() {
        assignments.forEach { (fieldInfo, pair) ->
          val className = pair.first
          val methodName = pair.second
          val descriptor = fieldInfo.descriptor
          println("fieldName: ${fieldInfo.name}, owner: ${fieldInfo.owner}, className: $className, methodName: $methodName, descriptor: $descriptor")
          mv.visitVarInsn(Opcodes.ALOAD, 0)
          mv.visitMethodInsn(Opcodes.INVOKESTATIC, className, methodName, "()$descriptor", false)
          mv.visitFieldInsn(Opcodes.PUTFIELD, fieldInfo.owner, fieldInfo.name, descriptor)
        }
      }
    }
  }
}