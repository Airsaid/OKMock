package com.airsaid.okmock;

import com.airsaid.okmock.api.MockValue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author airsaid
 */
public class OKMock {

  public static Object getMockData(String signature) {
    signature = getReferenceType(signature);
    List<String> formatSignatures = formatSignature(signature);
    return getInstanceRecursively(formatSignatures, 0, formatSignatures.size() - 1);
  }

  private static String getReferenceType(String descriptor) {
    switch (descriptor) {
      case "Z":
      case "boolean":
        return "Ljava/lang/Boolean;";
      case "C":
      case "char":
        return "Ljava/lang/Character;";
      case "B":
      case "byte":
        return "Ljava/lang/Byte;";
      case "S":
      case "short":
        return "Ljava/lang/Short;";
      case "I":
      case "int":
        return "Ljava/lang/Integer;";
      case "F":
      case "float":
        return "Ljava/lang/Float;";
      case "J":
      case "long":
        return "Ljava/lang/Long;";
      case "D":
      case "double":
        return "Ljava/lang/Double;";
    }
    return descriptor;
  }

  private static List<String> formatSignature(String signature) {
    List<String> result = new ArrayList<>();
    StringBuilder className = new StringBuilder();
    char[] chars = signature.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      char ch = chars[i];
      if (className.length() <= 0 && ch == 'L') {
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
      } else if (ch == '/') {
        className.append('.');
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

    String formatSignature = formatSignatures.get(start);
    if (!isClassName(formatSignature)) {
      return getInstanceRecursively(formatSignatures, start + 1, end);
    }

    Class<?> clazz = getClassByReflection(formatSignature);
    if (Boolean.class.isAssignableFrom(clazz)) {
      return getRandomBoolean();
    } else if (Character.class.isAssignableFrom(clazz)) {
      return getRandomChar();
    } else if (Byte.class.isAssignableFrom(clazz)) {
      return getRandomByte();
    } else if (Short.class.isAssignableFrom(clazz)) {
      return getRandomShort();
    } else if (Integer.class.isAssignableFrom(clazz)) {
      return getRandomInt();
    } else if (Float.class.isAssignableFrom(clazz)) {
      return getRandomFloat();
    } else if (Long.class.isAssignableFrom(clazz)) {
      return getRandomLong();
    } else if (Double.class.isAssignableFrom(clazz)) {
      return getRandomDouble();
    } else if (String.class.isAssignableFrom(clazz)) {
      return getRandomString();
    } else if (List.class.isAssignableFrom(clazz)) {
      List<Object> listInstance = getListInstance(clazz);
      Object params = getInstanceRecursively(formatSignatures, start + 1, end);
      if (params != null) {
        listInstance.add(params);
        randomForEach(index -> listInstance.add(getInstanceRecursively(formatSignatures, start + 1, end)));
      }
      return listInstance;
    } else if (Set.class.isAssignableFrom(clazz)) {
      Set<Object> setInstance = getSetInstance(clazz);
      Object params = getInstanceRecursively(formatSignatures, start + 1, end);
      if (params != null) {
        setInstance.add(params);
        randomForEach(index -> setInstance.add(getInstanceRecursively(formatSignatures, start + 1, end)));
      }
      return setInstance;
    } else if (Map.class.isAssignableFrom(clazz)) {
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
    } else {
      return getBean(clazz);
    }
  }

  private static boolean isClassName(String formatSignature) {
    return !formatSignature.equals("<") && !formatSignature.equals(">") && !formatSignature.equals(";");
  }

  private static Class<?> getClassByReflection(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private static boolean getRandomBoolean() {
    return RandomUtils.nextBoolean();
  }

  private static char getRandomChar() {
    return (char) RandomUtils.nextInt(49, 122);
  }

  private static byte getRandomByte() {
    return (byte) RandomUtils.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
  }

  private static short getRandomShort() {
    return (short) RandomUtils.nextInt(Short.MIN_VALUE, Short.MAX_VALUE);
  }

  private static int getRandomInt() {
    return RandomUtils.nextInt();
  }

  private static float getRandomFloat() {
    return RandomUtils.nextFloat();
  }

  private static long getRandomLong() {
    return RandomUtils.nextLong();
  }

  private static double getRandomDouble() {
    return RandomUtils.nextDouble();
  }

  private static String getRandomString() {
    return RandomStringUtils.random(RandomUtils.nextInt(1, 100), true, true);
  }

  @SuppressWarnings("unchecked")
  private static List<Object> getListInstance(Class<?> clazz) {
    if (clazz.isInterface()) {
      return new ArrayList<>();
    }
    return (List<Object>) getDefaultInstance(clazz);
  }

  @SuppressWarnings("unchecked")
  private static Set<Object> getSetInstance(Class<?> clazz) {
    if (clazz.isInterface()) {
      return new HashSet<>();
    }
    return (Set<Object>) getDefaultInstance(clazz);
  }

  @SuppressWarnings("unchecked")
  private static Map<Object, Object> getMapInstance(Class<?> clazz) {
    if (clazz.isInterface()) {
      return new HashMap<>();
    }
    return (Map<Object, Object>) getDefaultInstance(clazz);
  }

  private static Object getDefaultInstance(Class<?> clazz) {
    try {
      return clazz.getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  private static void randomForEach(IntConsumer consumer) {
    for (int i = 0; i <= RandomUtils.nextInt(1, 50); i++) {
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
      if (length > maxParamsCount) {
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

      if (parameterType instanceof Class<?>) {
        String className = ((Class<?>) parameterType).getName();
        result[i] = getMockData(className);
      } else {
        result[i] = getMockData(parameterType.toString().replace(",", ";").replace(" ", ""));
      }
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
        return booleanValues[RandomUtils.nextInt(0, booleanValues.length)];
      } else if (Character.TYPE.isAssignableFrom(parameter) || Character.class.isAssignableFrom(parameter)) {
        char[] charValues = mockValue.charValues();
        return charValues[RandomUtils.nextInt(0, charValues.length)];
      } else if (Byte.TYPE.isAssignableFrom(parameter) || Byte.class.isAssignableFrom(parameter)) {
        byte[] byteValues = mockValue.byteValues();
        return byteValues[RandomUtils.nextInt(0, byteValues.length)];
      } else if (Short.TYPE.isAssignableFrom(parameter) || Short.class.isAssignableFrom(parameter)) {
        short[] shortValues = mockValue.shortValues();
        return shortValues[RandomUtils.nextInt(0, shortValues.length)];
      } else if (Integer.TYPE.isAssignableFrom(parameter) || Integer.class.isAssignableFrom(parameter)) {
        int[] intValues = mockValue.intValues();
        return intValues[RandomUtils.nextInt(0, intValues.length)];
      } else if (Float.TYPE.isAssignableFrom(parameter) || Float.class.isAssignableFrom(parameter)) {
        float[] floatValues = mockValue.floatValues();
        return floatValues[RandomUtils.nextInt(0, floatValues.length)];
      } else if (Long.TYPE.isAssignableFrom(parameter) || Long.class.isAssignableFrom(parameter)) {
        long[] longValues = mockValue.longValues();
        return longValues[RandomUtils.nextInt(0, longValues.length)];
      } else if (Double.TYPE.isAssignableFrom(parameter) || Double.class.isAssignableFrom(parameter)) {
        double[] doubleValues = mockValue.doubleValues();
        return doubleValues[RandomUtils.nextInt(0, doubleValues.length)];
      } else if (String.class.isAssignableFrom(parameter)) {
        String[] stringValues = mockValue.stringValues();
        return stringValues[RandomUtils.nextInt(0, stringValues.length)];
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
