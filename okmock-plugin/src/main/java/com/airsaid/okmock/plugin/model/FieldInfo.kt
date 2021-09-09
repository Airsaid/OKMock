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

package com.airsaid.okmock.plugin.model

/**
 * Class field information.
 *
 * @param owner the internal name of the class in which the field is located.
 * @param access the field's access flags (see [org.objectweb.asm.Opcodes]). This parameter also indicates if
 *     the field is synthetic and/or deprecated.
 * @param name the field's name.
 * @param descriptor the field's descriptor (see [org.objectweb.asm.Type]).
 * @param signature the field's signature. May be null if the field's type does not use
 *     generic types.
 * @param value the field's value. This parameter, which may be null if the
 *     field does not have an value.
 * @param randomSizeRange the parameter of the [com.airsaid.okmock.OKMock] annotation on the field,
 *     used to specify random size range for mock data. default size range is 1 until 50.
 *
 * @author airsaid
 */
data class FieldInfo(
  val owner: String, val access: Int, val name: String,
  val descriptor: String, val signature: String?, val value: Any?,
  val randomSizeRange: IntArray = intArrayOf(1, 50)
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as FieldInfo

    if (owner != other.owner) return false
    if (access != other.access) return false
    if (name != other.name) return false
    if (descriptor != other.descriptor) return false
    if (signature != other.signature) return false
    if (value != other.value) return false
    if (!randomSizeRange.contentEquals(other.randomSizeRange)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = owner.hashCode()
    result = 31 * result + access
    result = 31 * result + name.hashCode()
    result = 31 * result + descriptor.hashCode()
    result = 31 * result + (signature?.hashCode() ?: 0)
    result = 31 * result + (value?.hashCode() ?: 0)
    result = 31 * result + randomSizeRange.contentHashCode()
    return result
  }
}