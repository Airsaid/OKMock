/*
 * Copyright 2021 Airsaid. https://github.com/airsaid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.airsaid.okmock.plugin

import com.airsaid.okmock.plugin.extension.OKMockExtension
import com.airsaid.okmock.plugin.transform.OKMockTransform
import com.airsaid.okmock.plugin.util.getExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author airsaid
 */
class OKMockPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    val extension = target.extensions.create(EXTENSION_NAME, OKMockExtension::class.java)
    target.getExtension().registerTransform(OKMockTransform(target, extension))
  }

  companion object {
    const val PLUGIN_NAME = "OKMock"
    const val EXTENSION_NAME = "okmock"
  }
}