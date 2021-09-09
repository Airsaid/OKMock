package com.airsaid.okmock.data.provider;

import com.airsaid.okmock.api.Mock;
import com.airsaid.okmock.data.Group;
import com.airsaid.okmock.data.Person;

import java.util.List;
import java.util.Map;

/**
 * @author airsaid
 */
public class ArrayMockDataProvider {

  @Mock
  public boolean[] booleanArray;

  @Mock(randomSizeRange = {1, 10})
  public boolean[][] booleanArrayDimension;

  @Mock
  public Boolean[] booleanWrapArray;

  @Mock(randomSizeRange = {1, 10})
  public Boolean[][] booleanWrapArrayDimension;

  @Mock
  public char[] charArray;

  @Mock(randomSizeRange = {1, 10})
  public char[][] charArrayDimension;

  @Mock
  public Character[] charWrapArray;

  @Mock(randomSizeRange = {1, 10})
  public Character[][] charWrapArrayDimension;

  @Mock
  public byte[] byteArray;

  @Mock(randomSizeRange = {1, 10})
  public byte[][] byteArrayDimension;

  @Mock
  public Byte[] byteWrapArray;

  @Mock(randomSizeRange = {1, 10})
  public Byte[][] byteWrapArrayDimension;

  @Mock
  public short[] shortArray;

  @Mock(randomSizeRange = {1, 10})
  public short[][] shortArrayDimension;

  @Mock
  public Short[] shortWrapArray;

  @Mock(randomSizeRange = {1, 10})
  public Short[][] shortWrapArrayDimension;

  @Mock
  public int[] intArray;

  @Mock(randomSizeRange = {1, 10})
  public int[][] intArrayDimension;

  @Mock
  public Integer[] intWrapArray;

  @Mock(randomSizeRange = {1, 10})
  public Integer[][] intWrapArrayDimension;

  @Mock
  public float[] floatArray;

  @Mock(randomSizeRange = {1, 10})
  public float[][] floatArrayDimension;

  @Mock
  public Float[] floatWrapArray;

  @Mock(randomSizeRange = {1, 10})
  public Float[][] floatWrapArrayDimension;

  @Mock
  public long[] longArray;

  @Mock(randomSizeRange = {1, 10})
  public long[][] longArrayDimension;

  @Mock
  public Long[] longWrapArray;

  @Mock(randomSizeRange = {1, 10})
  public Long[][] longWrapArrayDimension;

  @Mock
  public double[] doubleArray;

  @Mock(randomSizeRange = {1, 10})
  public double[][] doubleArrayDimension;

  @Mock
  public Double[] doubleWrapArray;

  @Mock(randomSizeRange = {1, 10})
  public Double[][] doubleWrapArrayDimension;

  @Mock
  public Person[] personArray;

  @Mock(randomSizeRange = {1, 10})
  public Person[][] personArrayDimension;

  @Mock
  public Group[] groupArray;

  @Mock(randomSizeRange = {1, 10})
  public Group[][] groupArrayDimension;

  @Mock(randomSizeRange = {1, 10})
  public List<Person>[] listArray;

  @Mock(randomSizeRange = {1, 10})
  public List<Person>[][] listArrayDimension;

  @Mock(randomSizeRange = {1, 10})
  public Map<Integer, Person>[] mapArray;

  @Mock(randomSizeRange = {1, 10})
  public Map<Integer, Person>[][] mapArrayDimension;
}
