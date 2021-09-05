package com.airsaid.okmock;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.Assert.*;

import com.airsaid.okmock.data.Color;

/**
 * @author airsaid
 */
public class OKMockTest {

  @Test
  public void getMockDataByDescriptor() {
    assertEquals(Boolean.class, OKMock.getMockData("Z").getClass());
    assertEquals(Character.class, OKMock.getMockData("C").getClass());
    assertEquals(Byte.class, OKMock.getMockData("B").getClass());
    assertEquals(Short.class, OKMock.getMockData("S").getClass());
    assertEquals(Integer.class, OKMock.getMockData("I").getClass());
    assertEquals(Float.class, OKMock.getMockData("F").getClass());
    assertEquals(Long.class, OKMock.getMockData("J").getClass());
    assertEquals(Double.class, OKMock.getMockData("D").getClass());

    assertEquals(Array.newInstance(Boolean.TYPE, 0).getClass(), OKMock.getMockData("[Z").getClass());
    assertEquals(Array.newInstance(Character.TYPE, 0).getClass(), OKMock.getMockData("[C").getClass());
    assertEquals(Array.newInstance(Byte.TYPE, 0).getClass(), OKMock.getMockData("[B").getClass());
    assertEquals(Array.newInstance(Short.TYPE, 0).getClass(), OKMock.getMockData("[S").getClass());
    assertEquals(Array.newInstance(Integer.TYPE, 0).getClass(), OKMock.getMockData("[I").getClass());
    assertEquals(Array.newInstance(Float.TYPE, 0).getClass(), OKMock.getMockData("[F").getClass());
    assertEquals(Array.newInstance(Long.TYPE, 0).getClass(), OKMock.getMockData("[J").getClass());
    assertEquals(Array.newInstance(Double.TYPE, 0).getClass(), OKMock.getMockData("[D").getClass());

    assertEquals(Array.newInstance(Array.newInstance(Boolean.TYPE, 0).getClass(), 0).getClass(), OKMock.getMockData("[[Z").getClass());
    assertEquals(Array.newInstance(Array.newInstance(Character.TYPE, 0).getClass(), 0).getClass(), OKMock.getMockData("[[C").getClass());
    assertEquals(Array.newInstance(Array.newInstance(Byte.TYPE, 0).getClass(), 0).getClass(), OKMock.getMockData("[[B").getClass());
    assertEquals(Array.newInstance(Array.newInstance(Short.TYPE, 0).getClass(), 0).getClass(), OKMock.getMockData("[[S").getClass());
    assertEquals(Array.newInstance(Array.newInstance(Integer.TYPE, 0).getClass(), 0).getClass(), OKMock.getMockData("[[I").getClass());
    assertEquals(Array.newInstance(Array.newInstance(Float.TYPE, 0).getClass(), 0).getClass(), OKMock.getMockData("[[F").getClass());
    assertEquals(Array.newInstance(Array.newInstance(Long.TYPE, 0).getClass(), 0).getClass(), OKMock.getMockData("[[J").getClass());
    assertEquals(Array.newInstance(Array.newInstance(Double.TYPE, 0).getClass(), 0).getClass(), OKMock.getMockData("[[D").getClass());

    assertEquals(Boolean.class, OKMock.getMockData("Ljava/lang/Boolean;").getClass());
    assertEquals(Character.class, OKMock.getMockData("Ljava/lang/Character;").getClass());
    assertEquals(Byte.class, OKMock.getMockData("Ljava/lang/Byte;").getClass());
    assertEquals(Short.class, OKMock.getMockData("Ljava/lang/Short;").getClass());
    assertEquals(Integer.class, OKMock.getMockData("Ljava/lang/Integer;").getClass());
    assertEquals(Float.class, OKMock.getMockData("Ljava/lang/Float;").getClass());
    assertEquals(Long.class, OKMock.getMockData("Ljava/lang/Long;").getClass());
    assertEquals(Double.class, OKMock.getMockData("Ljava/lang/Double;").getClass());

    assertEquals(Boolean[].class, OKMock.getMockData("[Ljava/lang/Boolean;").getClass());
    assertEquals(Character[].class, OKMock.getMockData("[Ljava/lang/Character;").getClass());
    assertEquals(Byte[].class, OKMock.getMockData("[Ljava/lang/Byte;").getClass());
    assertEquals(Short[].class, OKMock.getMockData("[Ljava/lang/Short;").getClass());
    assertEquals(Integer[].class, OKMock.getMockData("[Ljava/lang/Integer;").getClass());
    assertEquals(Float[].class, OKMock.getMockData("[Ljava/lang/Float;").getClass());
    assertEquals(Long[].class, OKMock.getMockData("[Ljava/lang/Long;").getClass());
    assertEquals(Double[].class, OKMock.getMockData("[Ljava/lang/Double;").getClass());

    assertEquals(Boolean[][].class, OKMock.getMockData("[[Ljava/lang/Boolean;").getClass());
    assertEquals(Character[][].class, OKMock.getMockData("[[Ljava/lang/Character;").getClass());
    assertEquals(Byte[][].class, OKMock.getMockData("[[Ljava/lang/Byte;").getClass());
    assertEquals(Short[][].class, OKMock.getMockData("[[Ljava/lang/Short;").getClass());
    assertEquals(Integer[][].class, OKMock.getMockData("[[Ljava/lang/Integer;").getClass());
    assertEquals(Float[][].class, OKMock.getMockData("[[Ljava/lang/Float;").getClass());
    assertEquals(Long[][].class, OKMock.getMockData("[[Ljava/lang/Long;").getClass());
    assertEquals(Double[][].class, OKMock.getMockData("[[Ljava/lang/Double;").getClass());
  }

  @Test
  public void getMockDataBySignature() {
    Object arrayList = OKMock.getMockData("Ljava/util/List<Ljava/lang/String;>;");
    assertEquals(ArrayList.class, arrayList.getClass());
    assertEquals(String.class, ((ArrayList<?>) arrayList).get(0).getClass());

    Object hashSet = OKMock.getMockData("Ljava/util/Set<Ljava/lang/String;>;");
    assertEquals(HashSet.class, hashSet.getClass());
    assertEquals(String.class, ((HashSet<?>) hashSet).iterator().next().getClass());

    Object hashMap = OKMock.getMockData("Ljava/util/Map<Ljava/lang/Integer;Ljava/lang/String;>;");
    assertEquals(HashMap.class, hashMap.getClass());
    assertEquals(Integer.class, ((HashMap<?, ?>) hashMap).keySet().iterator().next().getClass());
    assertEquals(String.class, ((HashMap<?, ?>) hashMap).values().iterator().next().getClass());
  }

  @Test
  public void getMockDataBySignatureNested() {
    Object linkedList = OKMock.getMockData("Ljava/util/LinkedList<Ljava/util/List<Ljava/lang/String;>;>;");
    assertEquals(LinkedList.class, linkedList.getClass());
    assertEquals(ArrayList.class, ((LinkedList<?>) linkedList).get(0).getClass());
    assertEquals(String.class, ((ArrayList<?>) ((LinkedList<?>) linkedList).get(0)).get(0).getClass());

    Object hashSet = OKMock.getMockData("Ljava/util/Set<Ljava/util/Set<Ljava/lang/String;>;>;");
    assertEquals(HashSet.class, hashSet.getClass());
    assertEquals(HashSet.class, ((HashSet<?>) hashSet).iterator().next().getClass());
    assertEquals(String.class, ((HashSet<?>) ((HashSet<?>) hashSet).iterator().next()).iterator().next().getClass());

    Object hashMap = OKMock.getMockData("Ljava/util/Map<Ljava/lang/Integer;Ljava/util/Map<Ljava/lang/Integer;Ljava/lang/String;>;>;");
    assertEquals(HashMap.class, hashMap.getClass());
    Object mapKey = ((HashMap<?, ?>) hashMap).keySet().iterator().next();
    assertEquals(Integer.class, mapKey.getClass());
    Object mapValue = ((HashMap<?, ?>) hashMap).values().iterator().next();
    assertEquals(HashMap.class, mapValue.getClass());
    assertEquals(Integer.class, ((HashMap<?, ?>) mapValue).keySet().iterator().next().getClass());
    assertEquals(String.class, ((HashMap<?, ?>) mapValue).values().iterator().next().getClass());
  }

  @Test
  public void getMockDataByEnum() {
    assertTrue(OKMock.getMockData("Lcom.airsaid.okmock.data.Color;") instanceof Color);

    Object colorList = OKMock.getMockData("Ljava/util/List<Lcom.airsaid.okmock.data.Color;>;");
    assertEquals(ArrayList.class, colorList.getClass());
    assertTrue(((ArrayList<?>) colorList).get(0) instanceof Color);
  }
}