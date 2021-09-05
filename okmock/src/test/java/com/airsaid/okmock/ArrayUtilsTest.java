package com.airsaid.okmock;

import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author airsaid
 */
public class ArrayUtilsTest {

  @Test
  public void getArray() {
    Object array = ArrayUtils.getArray(String.class, 1, 3, 3);
    int length = Array.getLength(array);
    assertEquals(3, length);

    for (int i = 0; i < length; i++) {
      Object elem = Array.get(array, i);
      assertNull(elem);
    }
  }

  @Test
  public void gArray2By2Dimension() {
    Object array = ArrayUtils.getArray(String.class, 2, 1, 1);
    assertEquals(1, Array.getLength(array));
    assertEquals(String[].class, Array.get(array, 0).getClass());
    assertEquals(1, Array.getLength(Array.get(array, 0)));
  }

  @Test
  public void getArrayDimension() {
    Class<?> arrayClass1 = Array.newInstance(String.class, 0).getClass();
    assertEquals(1, ArrayUtils.getArrayDimension(arrayClass1));

    Class<?> arrayClass2 = Array.newInstance(Array.newInstance(String.class, 0).getClass(), 0).getClass();
    assertEquals(2, ArrayUtils.getArrayDimension(arrayClass2));
  }

  @Test
  public void fillArrayData() {
    Object array = Array.newInstance(String.class, 2);
    ArrayUtils.fillArrayData(array, () -> "airsaid");
    int length = Array.getLength(array);
    assertEquals(2, length);
    assertEquals("airsaid", Array.get(array, 0));
    assertEquals("airsaid", Array.get(array, 1));
  }
}