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
 *
 * @author airsaid
 */
data class FieldInfo(
  val owner: String, val access: Int, val name: String,
  val descriptor: String, val signature: String?, val value: Any?
)