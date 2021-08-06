package com.airsaid.okmock.util

fun Any?.toDetailString(): String {
  if (this == null) return "null"

  if (this.javaClass.isArray) {
    if (BooleanArray::class.java.isAssignableFrom(this.javaClass)) {
      return (this as BooleanArray).contentToString()
    } else if (CharArray::class.java.isAssignableFrom(this.javaClass)) {
      return (this as CharArray).contentToString()
    } else if (ByteArray::class.java.isAssignableFrom(this.javaClass)) {
      return (this as ByteArray).contentToString()
    } else if (ShortArray::class.java.isAssignableFrom(this.javaClass)) {
      return (this as ShortArray).contentToString()
    } else if (IntArray::class.java.isAssignableFrom(this.javaClass)) {
      return (this as IntArray).contentToString()
    } else if (FloatArray::class.java.isAssignableFrom(this.javaClass)) {
      return (this as FloatArray).contentToString()
    } else if (LongArray::class.java.isAssignableFrom(this.javaClass)) {
      return (this as LongArray).contentToString()
    } else if (DoubleArray::class.java.isAssignableFrom(this.javaClass)) {
      return (this as DoubleArray).contentToString()
    }
    return (this as Array<*>).toDetailString()
  }

  return toString()
}

fun Array<*>?.toDetailString(): String {
  if (this == null) return "null"

  val b = StringBuilder()
  b.append('[')
  for (i in 0 until size) {
    b.append(this[i].toDetailString())
    if (i != size - 1) b.append(", ")
  }
  b.append(']')
  return b.toString()
}