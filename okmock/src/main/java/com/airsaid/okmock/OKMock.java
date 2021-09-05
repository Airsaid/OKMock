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

import com.airsaid.okmock.api.MockValue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class provides mock data. is the only public class.
 * <p>
 * It is not recommended to manually call its {@link #getMockData(String)} method,
 * it is recommended to use the {@link com.airsaid.okmock.api.Mock} annotation.
 *
 * @author airsaid
 * @see com.airsaid.okmock.api.Mock
 */
public class OKMock {

  /**
   * Returns the corresponding mock object through the specified type signature.
   * <p>
   * For example, if {@code signature} is {@code Z} descriptor, then return {@link Boolean} mock object.
   * if {@code signature} is an {@code [I} descriptor, then return {@link Integer} array mock object.
   * if {@code signature} is an {@code Ljava/lang/String;} descriptor, then return {@link String} mock object.
   * if {@code signature} is an {@code [Ljava/lang/Boolean;} descriptor, then return {@link Boolean} array mock object.
   * if {@code signature} is an {@code Ljava/util/List<Ljava/lang/String;>;} signature,
   * then return {@link List} mock object, and contains random {@link String} data.
   * <p>
   * The data is completely random by default, If you need custom data, you can view the {@link MockValue} annotation.
   *
   * @param signature the type descriptor or signature.
   * @return the mock object corresponding to the given type descriptor or signature.
   * @see MockValue
   */
  public static Object getMockData(String signature) {
    List<String> formatSignatures = formatSignature(signature);
    return getInstanceRecursively(formatSignatures, 0, formatSignatures.size() - 1);
  }

  private static List<String> formatSignature(String signature) {
    List<String> result = new ArrayList<>();
    StringBuilder className = new StringBuilder();
    char[] chars = signature.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      char ch = chars[i];
      if (ch == '+' || ch == '-') { // ignore wildcardIndicator
        continue;
      }
      if (ch == '<') {
        result.add(className.toString());
        result.add(String.valueOf(ch));
        className.setLength(0);
      } else if (ch == ';') {
        if (className.length() > 0) {
          result.add(className.toString());
          className.setLength(0);
        }
        if (i < chars.length - 2 && chars[i + 1] != '>') {
          result.add(String.valueOf(ch));
        }
      } else if (ch == '>') {
        result.add(String.valueOf(ch));
      } else {
        className.append(ch);
      }
    }
    if (className.length() > 0) {
      result.add(className.toString());
    }
    return result;
  }

  private static Object getInstanceRecursively(List<String> formatSignatures, int start, int end) {
    if (start > end) {
      return null;
    }

    String descriptor = formatSignatures.get(start);
    if (!isDescriptor(descriptor)) {
      return getInstanceRecursively(formatSignatures, start + 1, end);
    }

    Class<?> sourceClass = TypeUtils.getClass(descriptor);
    boolean isArray = Objects.requireNonNull(sourceClass).isArray();
    Class<?> componentType = TypeUtils.getOriginalComponentType(sourceClass);
    Object array = isArray ? ArrayUtils.getArray(componentType, ArrayUtils.getArrayDimension(sourceClass), 1, 50) : null;

    if (Boolean.class.isAssignableFrom(componentType) || Boolean.TYPE == componentType) {
      return isArray ? RandomDataProvider.getRandomBooleanArray(array) : RandomDataProvider.getRandomBoolean();
    } else if (Character.class.isAssignableFrom(componentType) || Character.TYPE == componentType) {
      return isArray ? RandomDataProvider.getRandomCharArray(array) : RandomDataProvider.getRandomChar();
    } else if (Byte.class.isAssignableFrom(componentType) || Byte.TYPE == componentType) {
      return isArray ? RandomDataProvider.getRandomByteArray(array) : RandomDataProvider.getRandomByte();
    } else if (Short.class.isAssignableFrom(componentType) || Short.TYPE == componentType) {
      return isArray ? RandomDataProvider.getRandomShortArray(array) : RandomDataProvider.getRandomShort();
    } else if (Integer.class.isAssignableFrom(componentType) || Integer.TYPE == componentType) {
      return isArray ? RandomDataProvider.getRandomIntArray(array) : RandomDataProvider.getRandomInt();
    } else if (Float.class.isAssignableFrom(componentType) || Float.TYPE == componentType) {
      return isArray ? RandomDataProvider.getRandomFloatArray(array) : RandomDataProvider.getRandomFloat();
    } else if (Long.class.isAssignableFrom(componentType) || Long.TYPE == componentType) {
      return isArray ? RandomDataProvider.getRandomLongArray(array) : RandomDataProvider.getRandomLong();
    } else if (Double.class.isAssignableFrom(componentType) || Double.TYPE == componentType) {
      return isArray ? RandomDataProvider.getRandomDoubleArray(array) : RandomDataProvider.getRandomDouble();
    } else if (String.class.isAssignableFrom(componentType)) {
      return isArray ? RandomDataProvider.getRandomShortArray(array) : RandomDataProvider.getRandomString();
    } else if (List.class.isAssignableFrom(componentType)) {
      return isArray ? ArrayUtils.fillArrayData(array, () ->
          getListData(componentType, formatSignatures, start, end)) :
          getListData(componentType, formatSignatures, start, end);
    } else if (Set.class.isAssignableFrom(componentType)) {
      return isArray ? ArrayUtils.fillArrayData(array, () ->
          getSetData(componentType, formatSignatures, start, end)) :
          getSetData(componentType, formatSignatures, start, end);
    } else if (Map.class.isAssignableFrom(componentType)) {
      return isArray ? ArrayUtils.fillArrayData(array, () ->
          getMapData(componentType, formatSignatures, start, end)) :
          getMapData(componentType, formatSignatures, start, end);
    } else {
      return isArray ? ArrayUtils.fillArrayData(array, () ->
          getBean(componentType)) :
          getBean(componentType);
    }
  }

  private static boolean isDescriptor(String formatSignature) {
    return !formatSignature.equals("<") && !formatSignature.equals(">") && !formatSignature.equals(";");
  }

  private static List<Object> getListData(Class<?> clazz, List<String> formatSignatures, int start, int end) {
    List<Object> listInstance = getListInstance(clazz);
    Object params = getInstanceRecursively(formatSignatures, start + 1, end);
    if (params != null) {
      listInstance.add(params);
      randomForEach(index -> listInstance.add(getInstanceRecursively(formatSignatures, start + 1, end)));
    }
    return listInstance;
  }

  @SuppressWarnings("unchecked")
  private static List<Object> getListInstance(Class<?> clazz) {
    if (clazz.isInterface()) {
      return new ArrayList<>();
    }
    return (List<Object>) getDefaultInstance(clazz);
  }

  private static Object getDefaultInstance(Class<?> clazz) {
    try {
      return clazz.getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  private static Set<Object> getSetData(Class<?> clazz, List<String> formatSignatures, int start, int end) {
    Set<Object> setInstance = getSetInstance(clazz);
    Object params = getInstanceRecursively(formatSignatures, start + 1, end);
    if (params != null) {
      setInstance.add(params);
      randomForEach(index -> setInstance.add(getInstanceRecursively(formatSignatures, start + 1, end)));
    }
    return setInstance;
  }

  @SuppressWarnings("unchecked")
  private static Set<Object> getSetInstance(Class<?> clazz) {
    if (clazz.isInterface()) {
      return new HashSet<>();
    }
    return (Set<Object>) getDefaultInstance(clazz);
  }

  private static Map<Object, Object> getMapData(Class<?> clazz, List<String> formatSignatures, int start, int end) {
    int mapPartitionIndex = findMapPartitionIndex(formatSignatures, start, end);
    Map<Object, Object> mapInstance = getMapInstance(clazz);
    Object key = getInstanceRecursively(formatSignatures, start + 1, mapPartitionIndex);
    Object value = getInstanceRecursively(formatSignatures, mapPartitionIndex + 1, end);
    if (key != null && value != null) {
      mapInstance.put(key, value);
      randomForEach(index ->
          mapInstance.put(getInstanceRecursively(formatSignatures, start + 1, mapPartitionIndex),
              getInstanceRecursively(formatSignatures, mapPartitionIndex + 1, end)));
    }
    return mapInstance;
  }

  @SuppressWarnings("unchecked")
  private static Map<Object, Object> getMapInstance(Class<?> clazz) {
    if (clazz.isInterface()) {
      return new HashMap<>();
    }
    return (Map<Object, Object>) getDefaultInstance(clazz);
  }

  private static void randomForEach(IntConsumer consumer) {
    for (int i = 0; i <= RandomDataProvider.nextInt(1, 50); i++) {
      consumer.accept(i);
    }
  }

  private static int findMapPartitionIndex(List<String> formatSignature, int start, int end) {
    int firstIndex = -1;
    for (int i = start; i <= end; i++) {
      String format = formatSignature.get(i);
      if (firstIndex == -1 && ";".equals(format)) {
        firstIndex = i;
      }
      if (";".equals(format) && i - 1 >= 0 && ">".equals(formatSignature.get(i - 1))) {
        return i;
      }
    }
    return firstIndex;
  }

  @SuppressWarnings(value = "unchecked")
  private static <T> T getBean(Class<T> clazz) {
    Constructor<?> constructor = getMaxParamsConstructor(clazz);
    if (constructor == null) { // maybe enum class
      return (T) RandomDataProvider.getRandomEnumInstance(clazz);
    }
    Type[] parameterTypes = constructor.getGenericParameterTypes();
    Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
    try {
      return (T) constructor.newInstance(getParameters(parameterTypes, parameterAnnotations));
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e.getCause());
    }
  }

  private static Constructor<?> getMaxParamsConstructor(Class<?> clazz) {
    Constructor<?>[] constructors = clazz.getConstructors();
    int maxParamsCount = -1;
    Constructor<?> result = null;
    for (Constructor<?> constructor : constructors) {
      int length = constructor.getParameterTypes().length;
      if (length > maxParamsCount && !constructor.isSynthetic()) {
        maxParamsCount = length;
        result = constructor;
      }
    }
    return result;
  }

  private static Object[] getParameters(Type[] parameterTypes, Annotation[][] parameterAnnotations) {
    Object[] result = new Object[parameterTypes.length];
    for (int i = 0; i < parameterTypes.length; i++) {
      Type parameterType = parameterTypes[i];
      Annotation[] parameterAnnotation = parameterAnnotations[i];
      Object parameter = getParameterForAnnotation(parameterType, parameterAnnotation);
      if (parameter != null) {
        result[i] = parameter;
        continue;
      }
      result[i] = getMockData(TypeUtils.getTypeDescriptor(parameterType));
    }
    return result;
  }

  private static Object getParameterForAnnotation(Type parameterType, Annotation[] parameterAnnotations) {
    if (!(parameterType instanceof Class<?>)) {
      return null;
    }
    Class<?> parameter = (Class<?>) parameterType;

    for (Annotation annotation : parameterAnnotations) {
      if (!(annotation instanceof MockValue)) {
        continue;
      }

      MockValue mockValue = (MockValue) annotation;
      if (Boolean.TYPE.isAssignableFrom(parameter) || Boolean.class.isAssignableFrom(parameter)) {
        boolean[] booleanValues = mockValue.booleanValues();
        return booleanValues[RandomDataProvider.nextInt(0, booleanValues.length)];
      } else if (Character.TYPE.isAssignableFrom(parameter) || Character.class.isAssignableFrom(parameter)) {
        char[] charValues = mockValue.charValues();
        return charValues[RandomDataProvider.nextInt(0, charValues.length)];
      } else if (Byte.TYPE.isAssignableFrom(parameter) || Byte.class.isAssignableFrom(parameter)) {
        byte[] byteValues = mockValue.byteValues();
        return byteValues[RandomDataProvider.nextInt(0, byteValues.length)];
      } else if (Short.TYPE.isAssignableFrom(parameter) || Short.class.isAssignableFrom(parameter)) {
        short[] shortValues = mockValue.shortValues();
        return shortValues[RandomDataProvider.nextInt(0, shortValues.length)];
      } else if (Integer.TYPE.isAssignableFrom(parameter) || Integer.class.isAssignableFrom(parameter)) {
        int[] intValues = mockValue.intValues();
        return intValues[RandomDataProvider.nextInt(0, intValues.length)];
      } else if (Float.TYPE.isAssignableFrom(parameter) || Float.class.isAssignableFrom(parameter)) {
        float[] floatValues = mockValue.floatValues();
        return floatValues[RandomDataProvider.nextInt(0, floatValues.length)];
      } else if (Long.TYPE.isAssignableFrom(parameter) || Long.class.isAssignableFrom(parameter)) {
        long[] longValues = mockValue.longValues();
        return longValues[RandomDataProvider.nextInt(0, longValues.length)];
      } else if (Double.TYPE.isAssignableFrom(parameter) || Double.class.isAssignableFrom(parameter)) {
        double[] doubleValues = mockValue.doubleValues();
        return doubleValues[RandomDataProvider.nextInt(0, doubleValues.length)];
      } else if (String.class.isAssignableFrom(parameter)) {
        String[] stringValues = mockValue.stringValues();
        return stringValues[RandomDataProvider.nextInt(0, stringValues.length)];
      } else {
        return null;
      }
    }
    return null;
  }

  @FunctionalInterface
  private interface IntConsumer {
    void accept(int value);
  }
}
