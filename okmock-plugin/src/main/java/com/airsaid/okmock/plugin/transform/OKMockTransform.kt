package com.airsaid.okmock.plugin.transform

import com.airsaid.okmock.api.Mock
import com.airsaid.okmock.plugin.OKMockPlugin
import com.airsaid.okmock.plugin.constant.Constants.PROVIDER_CLASS_NAME
import com.android.build.api.transform.TransformInvocation

/**
 * @author airsaid
 */
class OKMockTransform : AbstractTransform() {

  override fun getName() = OKMockPlugin.PLUGIN_NAME

  override fun onTransformBefore(invocation: TransformInvocation) {}

  override fun onTransform(invocation: TransformInvocation, bytecode: ByteArray): ByteArray {
    var classByteCode = bytecode

    val annotationFieldTransform = AnnotationFieldTransform(listOf(Mock::class.java))
    classByteCode = annotationFieldTransform.onTransform(invocation, classByteCode)

    val fields = annotationFieldTransform.getFields()
    classByteCode = FieldAssigneeTransform(fields, PROVIDER_CLASS_NAME).onTransform(invocation, classByteCode)

    return classByteCode
  }

  override fun onTransformAfter(invocation: TransformInvocation) {}
}