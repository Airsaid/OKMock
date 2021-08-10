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
 * This annotation is used to decorate the construction parameters of the bean class.
 * <p>
 * This annotation is used in conjunction with a {@link Mock} annotation to specify random data to populate.
 *
 * @author airsaid
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface MockValue {

  boolean[] booleanValues() default {};

  char[] charValues() default {};

  byte[] byteValues() default {};

  short[] shortValues() default {};

  int[] intValues() default {};

  float[] floatValues() default {};

  long[] longValues() default {};

  double[] doubleValues() default {};

  String[] stringValues() default {};
}
