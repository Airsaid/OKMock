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

package com.airsaid.okmock.plugin.extension

import org.gradle.api.provider.Property

/**
 * Configuration object for [com.airsaid.okmock.plugin.OKMockPlugin].
 *
 * @author airsaid
 */
abstract class OKMockExtension {

  /**
   * Whether to enable debug mode. default disabled.
   */
  abstract val enableDebug: Property<Boolean>

  init {
    enableDebug.convention(false)
  }
}