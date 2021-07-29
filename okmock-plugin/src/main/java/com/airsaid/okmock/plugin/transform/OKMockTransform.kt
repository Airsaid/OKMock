package com.airsaid.okmock.plugin.transform

import com.airsaid.okmock.plugin.OKMockPlugin
import com.android.build.api.transform.TransformInvocation

/**
 * @author airsaid
 */
class OKMockTransform : AbstractTransform() {

  override fun getName() = OKMockPlugin.PLUGIN_NAME

  override fun onTransformBefore(transformInvocation: TransformInvocation) {

  }

  override fun onTransform(transformInvocation: TransformInvocation, bytecode: ByteArray): ByteArray {
    return bytecode
  }

  override fun onTransformAfter(transformInvocation: TransformInvocation) {

  }
}