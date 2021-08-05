package com.airsaid.okmock;

import org.junit.Test;

import java.lang.reflect.Array;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author airsaid
 */
public class RandomDataProviderTest {

  @Test
  public void getRandomBoolean() {
    boolean randomBoolean = RandomDataProvider.getRandomBoolean();
    assertThat(randomBoolean, anyOf(is(true), is(false)));
  }

  @Test
  public void getRandomBooleanArray() {
    assertArray(RandomDataProvider.getRandomBooleanArray(
        Array.newInstance(Boolean.TYPE, 3)
    ), elem -> assertThat(elem, anyOf(is(true), is(false))));
  }

  @Test
  public void getRandomBooleanArrayBy2Dimension() {
    int[] dimensions = {3, 3};
    assertArray(RandomDataProvider.getRandomBooleanArray(
        Array.newInstance(Array.newInstance(Boolean.TYPE, 3).getClass().getComponentType(), dimensions)
    ), elem -> assertThat(elem, anyOf(is(true), is(false))));
  }

  @Test
  public void getRandomChar() {
    char randomChar = RandomDataProvider.getRandomChar();
    assertTrue(randomChar >= 49 && randomChar <= 122);
  }

  @Test
  public void getRandomCharArray() {
    assertArray(RandomDataProvider.getRandomCharArray(
        Array.newInstance(Character.TYPE, 3)
    ), elem -> assertSame(elem.getClass(), Character.class));
  }

  @Test
  public void getRandomCharArrayBy2Dimension() {
    int[] dimensions = {3, 3};
    assertArray(RandomDataProvider.getRandomCharArray(
        Array.newInstance(Array.newInstance(Character.TYPE, 3).getClass().getComponentType(), dimensions)
    ), elem -> assertSame(elem.getClass(), Character.class));
  }

  @Test
  public void getRandomByteArray() {
    assertArray(RandomDataProvider.getRandomByteArray(
        Array.newInstance(Byte.TYPE, 3)
    ), elem -> assertSame(elem.getClass(), Byte.class));
  }

  @Test
  public void getRandomByteArrayBy2Dimension() {
    int[] dimensions = {3, 3};
    assertArray(RandomDataProvider.getRandomByteArray(
        Array.newInstance(Array.newInstance(Byte.TYPE, 3).getClass().getComponentType(), dimensions)
    ), elem -> assertSame(elem.getClass(), Byte.class));
  }

  @Test
  public void getRandomShortArray() {
    assertArray(RandomDataProvider.getRandomShortArray(
        Array.newInstance(Short.TYPE, 3)
    ), elem -> assertSame(elem.getClass(), Short.class));
  }

  @Test
  public void getRandomShortArrayBy2Dimension() {
    int[] dimensions = {3, 3};
    assertArray(RandomDataProvider.getRandomShortArray(
        Array.newInstance(Array.newInstance(Short.TYPE, 3).getClass().getComponentType(), dimensions)
    ), elem -> assertSame(elem.getClass(), Short.class));
  }

  @Test
  public void getRandomIntArray() {
    assertArray(RandomDataProvider.getRandomIntArray(
        Array.newInstance(Integer.TYPE, 3)
    ), elem -> assertSame(elem.getClass(), Integer.class));
  }

  @Test
  public void getRandomIntArrayBy2Dimension() {
    int[] dimensions = {3, 3};
    assertArray(RandomDataProvider.getRandomIntArray(
        Array.newInstance(Array.newInstance(Integer.TYPE, 3).getClass().getComponentType(), dimensions)
    ), elem -> assertSame(elem.getClass(), Integer.class));
  }

  @Test
  public void getRandomFloatArray() {
    assertArray(RandomDataProvider.getRandomFloatArray(
        Array.newInstance(Float.TYPE, 3)
    ), elem -> assertSame(elem.getClass(), Float.class));
  }

  @Test
  public void getRandomFloatArrayBy2Dimension() {
    int[] dimensions = {3, 3};
    assertArray(RandomDataProvider.getRandomFloatArray(
        Array.newInstance(Array.newInstance(Float.TYPE, 3).getClass().getComponentType(), dimensions)
    ), elem -> assertSame(elem.getClass(), Float.class));
  }

  @Test
  public void getRandomLongArray() {
    assertArray(RandomDataProvider.getRandomLongArray(
        Array.newInstance(Long.TYPE, 3)
    ), elem -> assertSame(elem.getClass(), Long.class));
  }

  @Test
  public void getRandomLongArrayBy2Dimension() {
    int[] dimensions = {3, 3};
    assertArray(RandomDataProvider.getRandomLongArray(
        Array.newInstance(Array.newInstance(Long.TYPE, 3).getClass().getComponentType(), dimensions)
    ), elem -> assertSame(elem.getClass(), Long.class));
  }

  @Test
  public void getRandomDoubleArray() {
    assertArray(RandomDataProvider.getRandomDoubleArray(
        Array.newInstance(Double.TYPE, 3)
    ), elem -> assertSame(elem.getClass(), Double.class));
  }

  @Test
  public void getRandomDoubleArrayBy2Dimension() {
    int[] dimensions = {3, 3};
    assertArray(RandomDataProvider.getRandomDoubleArray(
        Array.newInstance(Array.newInstance(Double.TYPE, 3).getClass().getComponentType(), dimensions)
    ), elem -> assertSame(elem.getClass(), Double.class));
  }

  @Test
  public void getRandomStringArray() {
    assertArray(RandomDataProvider.getRandomStringArray(
        Array.newInstance(String.class, 3)
    ), elem -> assertSame(elem.getClass(), String.class));
  }

  @Test
  public void getRandomStringArrayBy2Dimension() {
    int[] dimensions = {3, 3};
    assertArray(RandomDataProvider.getRandomStringArray(
        Array.newInstance(Array.newInstance(String.class, 3).getClass().getComponentType(), dimensions)
    ), elem -> assertSame(elem.getClass(), String.class));
  }

  private void assertArray(Object array, ArrayDataAssert assertBlock) {
    int length = Array.getLength(array);
    for (int i = 0; i < length; i++) {
      Object elem = Array.get(array, i);
      if (elem.getClass().isArray()) {
        assertArray(elem, assertBlock);
      } else {
        assertBlock.assertArray(elem);
      }
    }
  }

  @FunctionalInterface
  private interface ArrayDataAssert {
    void assertArray(Object elem);
  }
}