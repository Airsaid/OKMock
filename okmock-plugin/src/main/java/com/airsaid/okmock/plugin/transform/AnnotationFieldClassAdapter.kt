package com.airsaid.okmock.plugin.transform

import com.airsaid.okmock.plugin.model.FieldInfo
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.Opcodes.ASM9
import org.objectweb.asm.Type

/**
 * @author airsaid
 */
class AnnotationFieldClassAdapter(
  private val fields: MutableList<FieldInfo>,
  private val annotations: List<Class<*>>,
  cv: ClassVisitor
) : ClassVisitor(ASM9, cv) {

  override fun visitField(
    access: Int,
    name: String?,
    descriptor: String?,
    signature: String?,
    value: Any?
  ): FieldVisitor {
    val fv = super.visitField(access, name, descriptor, signature, value)
    if (fv != null) {
      val fieldInfo = FieldInfo(access, name, descriptor, signature, value)
      return AnnotationFieldAdapter(fields, annotations, fieldInfo, fv)
    }
    return fv
  }

  private class AnnotationFieldAdapter(
    private val fields: MutableList<FieldInfo>,
    private val annotations: List<Class<*>>,
    private val field: FieldInfo,
    fv: FieldVisitor
  ) : FieldVisitor(ASM9, fv) {
    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
      annotations.forEach { annotation ->
        if (Type.getDescriptor(annotation) == descriptor) {
          fields.add(field)
        }
      }
      return super.visitAnnotation(descriptor, visible)
    }
  }
}