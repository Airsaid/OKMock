package com.airsaid.okmock;

import java.lang.reflect.Array;

/**
 * @author airsaid
 */
class RandomDataProvider {

  private RandomDataProvider() {

  }

  public static boolean getRandomBoolean() {
    return RandomUtils.nextBoolean();
  }

  public static Object getRandomBooleanArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomBoolean);
  }

  public static char getRandomChar() {
    return (char) RandomUtils.nextInt(49, 122);
  }

  public static Object getRandomCharArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomChar);
  }

  public static byte getRandomByte() {
    return (byte) RandomUtils.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
  }

  public static Object getRandomByteArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomByte);
  }

  public static short getRandomShort() {
    return (short) RandomUtils.nextInt(Short.MIN_VALUE, Short.MAX_VALUE);
  }

  public static Object getRandomShortArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomShort);
  }

  public static int getRandomInt() {
    return RandomUtils.nextInt();
  }

  public static Object getRandomIntArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomInt);
  }

  public static float getRandomFloat() {
    return RandomUtils.nextFloat();
  }

  public static Object getRandomFloatArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomFloat);
  }

  public static long getRandomLong() {
    return RandomUtils.nextLong();
  }

  public static Object getRandomLongArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomLong);
  }

  public static double getRandomDouble() {
    return RandomUtils.nextDouble();
  }

  public static Object getRandomDoubleArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomDouble);
  }

  public static String getRandomString() {
    return RandomStringUtils.random(RandomUtils.nextInt(1, 100), true, true);
  }

  public static Object getRandomStringArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomString);
  }

  public static Object getRandomArrayData(Object array, ArrayDataProvider provider) {
    int length = Array.getLength(array);
    for (int i = 0; i < length; i++) {
      Object elem = Array.get(array, i);
      if (elem != null && elem.getClass().isArray()) {
        Array.set(array, i, getRandomArrayData(elem, provider));
      } else {
        Array.set(array, i, provider.getArrayData());
      }
    }
    return array;
  }

  @FunctionalInterface
  private interface ArrayDataProvider {
    Object getArrayData();
  }
}
