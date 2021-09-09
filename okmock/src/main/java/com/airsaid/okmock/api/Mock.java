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

package com.airsaid.okmock.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify the field to be mocked.
 * <p>
 * The decorated fields cannot have assignment operations and will be
 * automatically populated with random data.
 *
 * @author airsaid
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Mock {

  /**
   * Returns specify a random size range for the mock data.
   *
   * @return random size range for the mock data.
   */
  int[] randomSizeRange() default {1, 50};

}