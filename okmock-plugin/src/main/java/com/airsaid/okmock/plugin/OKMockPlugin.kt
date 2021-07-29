package com.airsaid.okmock.plugin

import com.airsaid.okmock.plugin.transform.OKMockTransform
import com.airsaid.okmock.plugin.util.getExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author airsaid
 */
class OKMockPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    target.getExtension().registerTransform(OKMockTransform())
  }

  companion object {
    const val PLUGIN_NAME = "OKMock"
  }
}