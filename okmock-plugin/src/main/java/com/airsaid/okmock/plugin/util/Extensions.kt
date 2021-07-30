package com.airsaid.okmock.plugin.util

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.objectweb.asm.Type

fun Project.getExtension(): BaseExtension {
  val appExtension = extensions.findByType(AppExtension::class.java)
  if (appExtension != null) {
    return appExtension
  }

  val libraryExtension = extensions.findByType(LibraryExtension::class.java)
  if (libraryExtension != null) {
    return libraryExtension
  }

  throw GradleException("$displayName is not an Android project.")
}

fun Type.isPrimitiveType(): Boolean =
  this == Type.BOOLEAN_TYPE || this == Type.CHAR_TYPE || this == Type.BYTE_TYPE || this == Type.SHORT_TYPE
      || this == Type.INT_TYPE || this == Type.FLOAT_TYPE || this == Type.LONG_TYPE || this == Type.DOUBLE_TYPE

fun Type.isPrimitiveWrapType(): Boolean =
  this.isBooleanWrapType() || this.isCharWrapType() ||
      this.isByteWrapType() || this.isShortWrapType() ||
      this.isIntWrapType() || this.isFloatWrapType() ||
      this.isLongWrapType() || this.isDoubleWrapType()

fun Type.isBooleanWrapType() = this.descriptor == "Ljava/lang/Boolean;"

fun Type.isCharWrapType() = this.descriptor == "Ljava/lang/Character;"

fun Type.isByteWrapType() = this.descriptor == "Ljava/lang/Byte;"

fun Type.isShortWrapType() = this.descriptor == "Ljava/lang/Short;"

fun Type.isIntWrapType() = this.descriptor == "Ljava/lang/Integer;"

fun Type.isFloatWrapType() = this.descriptor == "Ljava/lang/Float;"

fun Type.isLongWrapType() = this.descriptor == "Ljava/lang/Long;"

fun Type.isDoubleWrapType() = this.descriptor == "Ljava/lang/Double;"
