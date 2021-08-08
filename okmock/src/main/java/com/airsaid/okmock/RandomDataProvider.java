package com.airsaid.okmock;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author airsaid
 */
class RandomDataProvider {

  private static ThreadLocalRandom random() {
    return ThreadLocalRandom.current();
  }

  private RandomDataProvider() {}

  public static boolean getRandomBoolean() {
    return random().nextBoolean();
  }

  public static Object getRandomBooleanArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomBoolean);
  }

  public static char getRandomChar() {
    return (char) nextInt(49, 122);
  }

  public static Object getRandomCharArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomChar);
  }

  public static byte getRandomByte() {
    return (byte) nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
  }

  public static Object getRandomByteArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomByte);
  }

  public static short getRandomShort() {
    return (short) nextInt(Short.MIN_VALUE, Short.MAX_VALUE);
  }

  public static Object getRandomShortArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomShort);
  }

  public static int getRandomInt() {
    return random().nextInt();
  }

  public static Object getRandomIntArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomInt);
  }

  public static float getRandomFloat() {
    return random().nextFloat();
  }

  public static Object getRandomFloatArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomFloat);
  }

  public static long getRandomLong() {
    return random().nextLong();
  }

  public static Object getRandomLongArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomLong);
  }

  public static double getRandomDouble() {
    return random().nextDouble();
  }

  public static Object getRandomDoubleArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomDouble);
  }

  public static String getRandomString() {
    return randomString(nextInt(1, 100), 0, 0, true, true, null, random());
  }

  public static Object getRandomStringArray(Object array) {
    return getRandomArrayData(array, RandomDataProvider::getRandomString);
  }

  public static int nextInt(final int startInclusive, final int endExclusive) {
    if (startInclusive == endExclusive) {
      return startInclusive;
    }

    return startInclusive + random().nextInt(endExclusive - startInclusive);
  }

  private static Object getRandomArrayData(Object array, ArrayDataProvider provider) {
    return fillArrayData(array, provider);
  }

  private static String randomString(int count, int start, int end, final boolean letters, final boolean numbers,
                                     final char[] chars, final Random random) {
    if (count == 0) {
      return "";
    }
    if (count < 0) {
      throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
    }
    if (chars != null && chars.length == 0) {
      throw new IllegalArgumentException("The chars array must not be empty");
    }

    if (start == 0 && end == 0) {
      if (chars != null) {
        end = chars.length;
      } else if (!letters && !numbers) {
        end = Character.MAX_CODE_POINT;
      } else {
        end = 'z' + 1;
        start = ' ';
      }
    } else if (end <= start) {
      throw new IllegalArgumentException("Parameter end (" + end + ") must be greater than start (" + start + ")");
    }

    final int zero_digit_ascii = 48;
    final int first_letter_ascii = 65;

    if (chars == null && (numbers && end <= zero_digit_ascii
        || letters && end <= first_letter_ascii)) {
      throw new IllegalArgumentException("Parameter end (" + end + ") must be greater then (" + zero_digit_ascii + ") for generating digits " +
          "or greater then (" + first_letter_ascii + ") for generating letters.");
    }

    final StringBuilder builder = new StringBuilder(count);
    final int gap = end - start;

    while (count-- != 0) {
      final int codePoint;
      if (chars == null) {
        codePoint = random.nextInt(gap) + start;

        switch (Character.getType(codePoint)) {
          case Character.UNASSIGNED:
          case Character.PRIVATE_USE:
          case Character.SURROGATE:
            count++;
            continue;
        }

      } else {
        codePoint = chars[random.nextInt(gap) + start];
      }

      final int numberOfChars = Character.charCount(codePoint);
      if (count == 0 && numberOfChars > 1) {
        count++;
        continue;
      }

      if (letters && Character.isLetter(codePoint)
          || numbers && Character.isDigit(codePoint)
          || !letters && !numbers) {
        builder.appendCodePoint(codePoint);

        if (numberOfChars == 2) {
          count--;
        }

      } else {
        count++;
      }
    }
    return builder.toString();
  }

  private static Object fillArrayData(Object array, ArrayDataProvider provider) {
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
  private interface ArrayDataProvider {
    Object getArrayData();
  }
}
