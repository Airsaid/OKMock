package com.airsaid.okmock.plugin.generator

import com.airsaid.okmock.plugin.constant.Constants.PROVIDER_CLASS_NAME
import com.airsaid.okmock.plugin.model.FieldInfo
import com.airsaid.okmock.plugin.util.*
import com.google.common.io.Files
import org.gradle.api.GradleException
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random


/**
 * @author airsaid
 */
object ClassGenerator {

  fun generate(outputFile: File, fields: List<FieldInfo>) {
    if (fields.isEmpty()) return

    Files.createParentDirs(outputFile)

    val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES)
    cw.visit(V1_8, ACC_PUBLIC or ACC_SUPER, PROVIDER_CLASS_NAME, null, Type.getInternalName(Object::class.java), null)
    cw.visitSource("OKMockProvider.java", null)

    generateConstructor(cw)

    fields.forEach { field -> generateMethod(cw, field) }

    cw.visitEnd()

    val classBytecode = cw.toByteArray()
    FileOutputStream(outputFile).use {
      it.write(classBytecode)
    }
  }

  private fun generateConstructor(cw: ClassWriter) {
    val mv = cw.visitMethod(ACC_PRIVATE, "<init>", "()V", null, null)
    mv.visitCode()
    mv.visitVarInsn(ALOAD, 0)
    mv.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(Object::class.java), "<init>", "()V", false)
    mv.visitInsn(RETURN)
    mv.visitMaxs(1, 1)
    mv.visitEnd()
  }

  private fun generateMethod(cw: ClassWriter, field: FieldInfo) {
    println("generateMethod: $field")

    val fieldType = Type.getType(field.descriptor)
    val methodDescriptor = Type.getMethodDescriptor(fieldType)
    val mv = cw.visitMethod(ACC_PUBLIC or ACC_STATIC, field.getGetterMethodName(), methodDescriptor, null, null)

    if (fieldType.isPrimitiveType()) {
      generatePrimitiveType(mv, fieldType)
    } else if (fieldType.isPrimitiveWrapType()) {
      generatePrimitiveWrappingType(mv, fieldType)
    }
  }

  private fun generatePrimitiveType(mv: MethodVisitor, fieldType: Type) {
    mv.visitCode()
    if (fieldType == Type.BOOLEAN_TYPE) {
      mv.visitInsn(if (randomBoolean()) ICONST_1 else ICONST_0)
      mv.visitInsn(IRETURN)
      mv.visitMaxs(1, 0)
    } else if (fieldType == Type.CHAR_TYPE) {
      mv.visitIntInsn(BIPUSH, randomCharValue())
      mv.visitInsn(IRETURN)
      mv.visitMaxs(1, 0)
    } else if (fieldType == Type.BYTE_TYPE) {
      mv.visitIntInsn(BIPUSH, randomByteValue())
      mv.visitInsn(IRETURN)
      mv.visitMaxs(1, 0)
    } else if (fieldType == Type.SHORT_TYPE) {
      mv.visitIntInsn(SIPUSH, randomShortValue())
      mv.visitInsn(IRETURN)
      mv.visitMaxs(1, 0)
    } else if (fieldType == Type.INT_TYPE) {
      mv.visitLdcInsn(randomIntValue())
      mv.visitInsn(IRETURN)
      mv.visitMaxs(1, 0)
    } else if (fieldType == Type.FLOAT_TYPE) {
      mv.visitLdcInsn(randomFloatValue())
      mv.visitInsn(FRETURN)
      mv.visitMaxs(1, 0)
    } else if (fieldType == Type.LONG_TYPE) {
      mv.visitLdcInsn(randomLongValue())
      mv.visitInsn(LRETURN)
      mv.visitMaxs(2, 0)
    } else if (fieldType == Type.DOUBLE_TYPE) {
      mv.visitLdcInsn(randomDoubleValue())
      mv.visitInsn(DRETURN)
      mv.visitMaxs(2, 0)
    } else {
      throw GradleException("Unknown field type: $fieldType")
    }
    mv.visitEnd()
  }

  private fun generatePrimitiveWrappingType(mv: MethodVisitor, fieldType: Type) {
    mv.visitCode()
    if (fieldType.isBooleanWrapType()) {
      mv.visitInsn(if (randomBoolean()) ICONST_1 else ICONST_0)
      mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false)
      mv.visitInsn(ARETURN)
      mv.visitMaxs(1, 0)
    } else if (fieldType.isCharWrapType()) {
      mv.visitIntInsn(BIPUSH, randomCharValue())
      mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false)
      mv.visitInsn(ARETURN)
      mv.visitMaxs(1, 0)
    } else if (fieldType.isByteWrapType()) {
      mv.visitIntInsn(BIPUSH, randomByteValue())
      mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false)
      mv.visitInsn(ARETURN)
      mv.visitMaxs(1, 0)
    } else if (fieldType.isShortWrapType()) {
      mv.visitIntInsn(SIPUSH, randomShortValue())
      mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false)
      mv.visitInsn(ARETURN)
      mv.visitMaxs(1, 0)
    } else if (fieldType.isIntWrapType()) {
      mv.visitLdcInsn(randomIntValue())
      mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false)
      mv.visitInsn(ARETURN)
      mv.visitMaxs(1, 0)
    } else if (fieldType.isFloatWrapType()) {
      mv.visitLdcInsn(randomFloatValue())
      mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false)
      mv.visitInsn(ARETURN)
      mv.visitMaxs(1, 0)
    } else if (fieldType.isLongWrapType()) {
      mv.visitLdcInsn(randomLongValue())
      mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false)
      mv.visitInsn(ARETURN)
      mv.visitMaxs(2, 0)
    } else if (fieldType.isDoubleWrapType()) {
      mv.visitLdcInsn(randomDoubleValue())
      mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false)
      mv.visitInsn(ARETURN)
      mv.visitMaxs(2, 0)
    } else {
      throw GradleException("Unknown field type: $fieldType")
    }
    mv.visitEnd()
  }

  private fun randomBoolean() = Random.nextBoolean()

  private fun randomCharValue() = Random.nextInt(49, 122)

  private fun randomByteValue() = Random.nextInt(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt())

  private fun randomShortValue() = Random.nextInt(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())

  private fun randomIntValue() = Random.nextInt()

  private fun randomFloatValue() = Random.nextFloat()

  private fun randomLongValue() = Random.nextLong()

  private fun randomDoubleValue() = Random.nextDouble()
}