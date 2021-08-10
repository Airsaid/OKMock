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

fun Type.isStringType() = this.descriptor == "Ljava/lang/String;"

fun Type.toPrimitiveType(): Type {
  if (isBooleanWrapType()) {
    return Type.BOOLEAN_TYPE
  } else if (isCharWrapType()) {
    return Type.CHAR_TYPE
  } else if (isByteWrapType()) {
    return Type.BYTE_TYPE
  } else if (isShortWrapType()) {
    return Type.SHORT_TYPE
  } else if (isIntWrapType()) {
    return Type.INT_TYPE
  } else if (isFloatWrapType()) {
    return Type.FLOAT_TYPE
  } else if (isLongWrapType()) {
    return Type.LONG_TYPE
  } else if (isDoubleWrapType()) {
    return Type.DOUBLE_TYPE
  }
  throw IllegalArgumentException("$this is not a primitive wrap type.")
}

fun Type.toPrimitiveWrapType(): Type {
  if (this == Type.BOOLEAN_TYPE) {
    return Type.getType("Ljava/lang/Boolean;")
  } else if (this == Type.CHAR_TYPE) {
    return Type.getType("Ljava/lang/Character;")
  } else if (this == Type.BYTE_TYPE) {
    return Type.getType("Ljava/lang/Byte;")
  } else if (this == Type.SHORT_TYPE) {
    return Type.getType("Ljava/lang/Short;")
  } else if (this == Type.INT_TYPE) {
    return Type.getType("Ljava/lang/Integer;")
  } else if (this == Type.FLOAT_TYPE) {
    return Type.getType("Ljava/lang/Float;")
  } else if (this == Type.LONG_TYPE) {
    return Type.getType("Ljava/lang/Long;")
  } else if (this == Type.DOUBLE_TYPE) {
    return Type.getType("Ljava/lang/Double;")
  }
  throw IllegalArgumentException("$this is not a primitive type.")
}
