package com.airsaid.okmock.plugin.transform

import com.airsaid.okmock.api.Mock
import com.airsaid.okmock.plugin.OKMockPlugin
import com.airsaid.okmock.plugin.constant.Constants.PROVIDER_CLASS_NAME
import com.airsaid.okmock.plugin.generator.ClassGenerator
import com.airsaid.okmock.plugin.model.FieldInfo
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInvocation
import java.io.File
import java.util.*

/**
 * @author airsaid
 */
class OKMockTransform : AbstractTransform() {

  private val mFields = mutableListOf<FieldInfo>()

  override fun getName() = OKMockPlugin.PLUGIN_NAME

  override fun onTransformBefore(invocation: TransformInvocation) {}

  override fun onTransform(invocation: TransformInvocation, bytecode: ByteArray): ByteArray {
    var classByteCode = bytecode

    val annotationFieldTransform = AnnotationFieldTransform(listOf(Mock::class.java))
    classByteCode = annotationFieldTransform.onTransform(invocation, classByteCode)

    val fields = annotationFieldTransform.getFields()
    mFields.addAll(fields)
    classByteCode = FieldAssigneeTransform(fields, PROVIDER_CLASS_NAME).onTransform(invocation, classByteCode)

    return classByteCode
  }

  override fun onTransformAfter(invocation: TransformInvocation) {
    val outputDir = invocation.outputProvider.getContentLocation(
      UUID.randomUUID().toString(),
      setOf(QualifiedContent.DefaultContentType.CLASSES),
      mutableSetOf(QualifiedContent.Scope.PROJECT),
      Format.DIRECTORY
    )
    val outputFile = File(outputDir, "$PROVIDER_CLASS_NAME.class")
    ClassGenerator.generate(outputFile, mFields)
  }
}