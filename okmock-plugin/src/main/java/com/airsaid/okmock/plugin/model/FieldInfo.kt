package com.airsaid.okmock.plugin.model

import android.databinding.tool.ext.capitalizeUS

/**
 * @author airsaid
 */
data class FieldInfo(val owner: String, val access: Int, val name: String, val descriptor: String, val signature: String?, val value: Any?) {
  fun getGetterMethodName(): String {
    return "get${name.capitalizeUS()}"
  }
}