package com.airsaid.okmock.plugin.transform

import com.android.build.api.transform.TransformInvocation

/**
 * @author airsaid
 */
interface TransformHandle {

  fun onTransformBefore(transformInvocation: TransformInvocation)

  fun onTransform(transformInvocation: TransformInvocation, bytecode: ByteArray): ByteArray

  fun onTransformAfter(transformInvocation: TransformInvocation)

}