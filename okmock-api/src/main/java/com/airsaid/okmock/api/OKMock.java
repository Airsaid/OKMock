package com.airsaid.okmock.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author airsaid
 */
public class OKMock {

  private static final Random random = new Random();

  public static boolean getBoolean() {
    return random.nextBoolean();
  }

  public static char getChar() {
    return (char) randomInt(49, 122);
  }

  public static byte getByte() {
    return (byte) randomInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
  }

  public static short getShort() {
    return (short) randomInt(Short.MIN_VALUE, Short.MAX_VALUE);
  }

  public static int getInt() {
    return random.nextInt();
  }

  public static float getFloat() {
    return random.nextFloat();
  }

  public static long getLong() {
    return random.nextLong();
  }

  public static double getDouble() {
    return random.nextDouble();
  }

  public static String getString() {
    byte[] bytes = new byte[randomInt(1, 100)];
    random.nextBytes(bytes);
    return new String(bytes, StandardCharsets.UTF_8);
  }

  @SuppressWarnings(value = "unchecked")
  public static <T> T getPojo(Class<T> clazz) {
    Constructor<?> constructor = getMaxParamsConstructor(clazz);
    Type[] parameterTypes = constructor.getGenericParameterTypes();
    try {
      return (T) constructor.newInstance(getParameters(parameterTypes));
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    throw new RuntimeException("Cannot find: " + clazz);
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
      if (Types.isBoolean(parameterType)) {
        result[i] = getBoolean();
      } else if (Types.isCharacter(parameterType)) {
        result[i] = getChar();
      } else if (Types.isByte(parameterType)) {
        result[i] = getByte();
      } else if (Types.isShort(parameterType)) {
        result[i] = getShort();
      } else if (Types.isInteger(parameterType)) {
        result[i] = getInt();
      } else if (Types.isFloat(parameterType)) {
        result[i] = getFloat();
      } else if (Types.isLong(parameterType)) {
        result[i] = getLong();
      } else if (Types.isDouble(parameterType)) {
        result[i] = getDouble();
      } else if (Types.isString(parameterType)) {
        result[i] = getString();
      }
    }
    return result;
  }

  private static int randomInt(int min, int max) {
    int upperBound = max - min + 1;
    return min + random.nextInt(upperBound);
  }
}
