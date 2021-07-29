package com.airsaid.okmock.plugin.transform

import com.android.build.api.transform.TransformInvocation

/**
 * @author airsaid
 */
abstract class AbsTransformHandler : TransformHandle {
  override fun onTransformBefore(invocation: TransformInvocation) {}

  override fun onTransform(invocation: TransformInvocation, bytecode: ByteArray) = bytecode

  override fun onTransformAfter(invocation: TransformInvocation) {}
}