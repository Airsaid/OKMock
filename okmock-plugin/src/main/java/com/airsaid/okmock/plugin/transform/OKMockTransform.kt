package com.airsaid.okmock.plugin.transform

import com.airsaid.okmock.api.Mock
import com.airsaid.okmock.plugin.OKMockPlugin
import com.airsaid.okmock.plugin.model.FieldInfo
import com.android.build.api.transform.TransformInvocation
import org.objectweb.asm.*

/**
 * @author airsaid
 */
class OKMockTransform : AbstractTransform() {

  private val mFieldInfoList = mutableListOf<FieldInfo>()

  override fun getName() = OKMockPlugin.PLUGIN_NAME

  override fun onTransformBefore(transformInvocation: TransformInvocation) {

  }

  override fun onTransform(
    transformInvocation: TransformInvocation,
    bytecode: ByteArray
  ): ByteArray {
    val cr = ClassReader(bytecode)
    val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES)
    val cv = AnnotationFieldClassAdapter(mFieldInfoList, listOf(Mock::class.java), cw)
    cr.accept(cv, 0)
    return cw.toByteArray()
  }

  override fun onTransformAfter(transformInvocation: TransformInvocation) {
    mFieldInfoList.forEach {
      println(it)
    }
  }
}