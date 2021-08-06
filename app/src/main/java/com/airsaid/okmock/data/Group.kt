package com.airsaid.okmock.data

/**
 * @author airsaid
 */
data class Group(val id: Long, val children: List<Long>, val arrays: IntArray) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Group

    if (id != other.id) return false
    if (children != other.children) return false
    if (!arrays.contentEquals(other.arrays)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + children.hashCode()
    result = 31 * result + arrays.contentHashCode()
    return result
  }
}
