package com.airsaid.okmock;

import java.lang.reflect.Array;

/**
 * @author airsaid
 */
class ArrayUtils {

  public static Object getArray(Class<?> sourceClass, Class<?> componentType, int startInclusive, int endExclusive) {
    return ArrayUtils.getArray(componentType, getArrayDimension(sourceClass), startInclusive, endExclusive);
  }

  public static Object getArray(Class<?> componentType, int dimension, int startInclusive, int endExclusive) {
    int[] arrayDimensions = new int[dimension];
    for (int i = 0; i < dimension; i++) {
      arrayDimensions[i] = RandomDataProvider.nextInt(startInclusive, endExclusive);
    }
    return Array.newInstance(componentType, arrayDimensions);
  }

  public static int getArrayDimension(Class<?> clazz) {
    int dimension = 0;
    while (clazz.getComponentType() != null) {
      clazz = clazz.getComponentType();
      dimension++;
    }
    return dimension;
  }

  public static Object fillArrayData(Object array, Object value) {
    int length = Array.getLength(array);
    for (int i = 0; i < length; i++) {
      Object elem = Array.get(array, i);
      if (elem != null && elem.getClass().isArray()) {
        Array.set(array, i, fillArrayData(elem, value));
      } else {
        Array.set(array, i, value);
      }
    }
    return array;
  }
}
