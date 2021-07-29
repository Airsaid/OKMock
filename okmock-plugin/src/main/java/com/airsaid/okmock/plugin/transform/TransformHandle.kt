package com.airsaid.okmock.plugin.transform

import com.android.build.api.transform.TransformInvocation

/**
 * @author airsaid
 */
interface TransformHandle {

  fun onTransformBefore(invocation: TransformInvocation)

  fun onTransform(invocation: TransformInvocation, bytecode: ByteArray): ByteArray

  fun onTransformAfter(invocation: TransformInvocation)

}