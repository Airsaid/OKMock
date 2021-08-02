package com.airsaid.okmock;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @author airsaid
 */
public class OKMock {

  private static final Random random = new Random();

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

  public static List<String> formatSignature(String signature) {
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
        mapInstance.put(getInstanceRecursively(formatSignatures, start + 1, mapPartitionIndex),
            getInstanceRecursively(formatSignatures, mapPartitionIndex + 1, end));
      }
      return mapInstance;
    } else {
      return getPojo(clazz);
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
    return random.nextBoolean();
  }

  private static char getRandomChar() {
    return (char) randomInt(49, 122);
  }

  private static byte getRandomByte() {
    return (byte) randomInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
  }

  private static short getRandomShort() {
    return (short) randomInt(Short.MIN_VALUE, Short.MAX_VALUE);
  }

  private static int getRandomInt() {
    return random.nextInt();
  }

  private static float getRandomFloat() {
    return random.nextFloat();
  }

  private static long getRandomLong() {
    return random.nextLong();
  }

  private static double getRandomDouble() {
    return random.nextDouble();
  }

  private static String getRandomString() {
    byte[] bytes = new byte[randomInt(1, 100)];
    random.nextBytes(bytes);
    return new String(bytes, StandardCharsets.UTF_8);
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
    for (int i = 0; i <= randomInt(1, 100); i++) {
      consumer.accept(i);
    }
  }

  protected static int findMapPartitionIndex(List<String> formatSignature, int start, int end) {
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
  private static <T> T getPojo(Class<T> clazz) {
    Constructor<?> constructor = getMaxParamsConstructor(clazz);
    Type[] parameterTypes = constructor.getGenericParameterTypes();
    try {
      return (T) constructor.newInstance(getParameters(parameterTypes));
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

  private static Object[] getParameters(Type[] parameterTypes) {
    Object[] result = new Object[parameterTypes.length];
    for (int i = 0; i < parameterTypes.length; i++) {
      Type parameterType = parameterTypes[i];
      if (parameterType instanceof Class<?>) {
        String className = ((Class<?>) parameterType).getName();
        result[i] = getMockData(className);
      } else {
        result[i] = getMockData(parameterType.toString().replace(",", ";").replace(" ", ""));
      }
    }
    return result;
  }

  private static int randomInt(int min, int max) {
    int upperBound = max - min + 1;
    return min + random.nextInt(upperBound);
  }

  @FunctionalInterface
  private interface IntConsumer {
    void accept(int value);
  }
}
