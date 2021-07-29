package com.airsaid.okmock.plugin.transform

import com.airsaid.okmock.plugin.model.FieldInfo
import com.android.build.api.transform.TransformInvocation
import org.objectweb.asm.*

/**
 * @author airsaid
 */
class AnnotationFieldTransform(private val annotations: List<Class<*>>) : AbsTransformHandler() {

  private val fields = mutableListOf<FieldInfo>()

  override fun onTransform(invocation: TransformInvocation, bytecode: ByteArray): ByteArray {
    val cr = ClassReader(bytecode)
    val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES)
    val cv = AnnotationFieldClassAdapter(fields, annotations, cw)
    cr.accept(cv, 0)
    return cw.toByteArray()
  }

  fun getFields(): List<FieldInfo> {
    return fields
  }

  private class AnnotationFieldClassAdapter(
    private val results: MutableList<FieldInfo>,
    private val annotations: List<Class<*>>,
    cv: ClassVisitor
  ) : ClassVisitor(Opcodes.ASM9, cv) {

    private lateinit var owner: String

    override fun visit(version: Int, access: Int, name: String, signature: String?, superName: String?, interfaces: Array<out String>?) {
      this.owner = name
      super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitField(access: Int, name: String, descriptor: String, signature: String?, value: Any?): FieldVisitor {
      val fv = super.visitField(access, name, descriptor, signature, value)
      if (fv != null) {
        val fieldInfo = FieldInfo(owner, access, name, descriptor, signature, value)
        return AnnotationFieldAdapter(results, annotations, fieldInfo, fv)
      }
      return fv
    }

    private class AnnotationFieldAdapter(
      private val results: MutableList<FieldInfo>,
      private val annotations: List<Class<*>>,
      private val field: FieldInfo,
      fv: FieldVisitor
    ) : FieldVisitor(Opcodes.ASM9, fv) {
      override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        annotations.forEach { annotation ->
          if (Type.getDescriptor(annotation) == descriptor) {
            results.add(field)
          }
        }
        return super.visitAnnotation(descriptor, visible)
      }
    }
  }
}