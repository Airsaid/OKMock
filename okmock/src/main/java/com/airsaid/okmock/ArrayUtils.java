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

package com.airsaid.okmock;

import java.lang.reflect.Array;

/**
 * This class can be used to manipulate arrays more easily.
 *
 * @author airsaid
 */
class ArrayUtils {

  /**
   * Creates a new array with the specified component type and size.
   *
   * @param componentType the {@code Class} object representing the component type of the new array.
   * @param dimension     dimension of the array.
   * @param size          default size of the array.
   * @return the array object.
   */
  public static Object getArray(Class<?> componentType, int dimension, int size) {
    int[] arrayDimensions = new int[dimension];
    for (int i = 0; i < dimension; i++) {
      arrayDimensions[i] = size;
    }
    return Array.newInstance(componentType, arrayDimensions);
  }

  /**
   * Returns the dimension of the specified array class.
   *
   * @param clazz the array class.
   * @return the dimension of the specified array class.
   */
  public static int getArrayDimension(Class<?> clazz) {
    int dimension = 0;
    while (clazz.getComponentType() != null) {
      clazz = clazz.getComponentType();
      dimension++;
    }
    return dimension;
  }

  /**
   * Fills the specified array object with the specified value object.
   *
   * @param array    the specified array object.
   * @param provider the specified functional interface of value provider.
   * @return the array object is filled.
   */
  public static Object fillArrayData(Object array, ArrayDataProvider provider) {
    int length = Array.getLength(array);
    for (int i = 0; i < length; i++) {
      Object elem = Array.get(array, i);
      if (elem != null && elem.getClass().isArray()) {
        Array.set(array, i, fillArrayData(elem, provider));
      } else {
        Array.set(array, i, provider.getArrayData());
      }
    }
    return array;
  }

  @FunctionalInterface
  public interface ArrayDataProvider {
    Object getArrayData();
  }
}
