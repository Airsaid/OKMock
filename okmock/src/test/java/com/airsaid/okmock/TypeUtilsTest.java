package com.airsaid.okmock;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author airsaid
 */
public class TypeUtilsTest {

  @Test
  public void getClassName() {
    assertEquals("", TypeUtils.getClassName(null));
    assertEquals("", TypeUtils.getClassName(""));
    assertEquals("", TypeUtils.getClassName("Z"));
    assertEquals("java.lang.String", TypeUtils.getClassName("Ljava/lang/String;"));
    assertEquals("java.lang.String", TypeUtils.getClassName("[Ljava/lang/String;"));
    assertEquals("java.lang.String", TypeUtils.getClassName("[[Ljava/lang/String;"));
    assertEquals("java.lang.String", TypeUtils.getClassName("java/lang/String;"));
    assertEquals("java.lang.String", TypeUtils.getClassName("java/lang/String"));
  }

  @Test
  public void isPrimitiveType() {
    assertTrue(TypeUtils.isPrimitiveType("Z"));
    assertTrue(TypeUtils.isPrimitiveType("C"));
    assertTrue(TypeUtils.isPrimitiveType("B"));
    assertTrue(TypeUtils.isPrimitiveType("S"));
    assertTrue(TypeUtils.isPrimitiveType("I"));
    assertTrue(TypeUtils.isPrimitiveType("F"));
    assertTrue(TypeUtils.isPrimitiveType("J"));
    assertTrue(TypeUtils.isPrimitiveType("D"));

    assertFalse(TypeUtils.isPrimitiveType(null));
    assertFalse(TypeUtils.isPrimitiveType(""));
    assertFalse(TypeUtils.isPrimitiveType(" "));
    assertFalse(TypeUtils.isPrimitiveType("ABC"));
  }

  @Test
  public void isBooleanType() {
    assertTrue(TypeUtils.isBooleanType("Z"));

    assertFalse(TypeUtils.isBooleanType(""));
    assertFalse(TypeUtils.isBooleanType(" "));
    assertFalse(TypeUtils.isBooleanType("ABC"));
  }

  @Test
  public void isCharType() {
    assertTrue(TypeUtils.isCharType("C"));

    assertFalse(TypeUtils.isCharType(""));
    assertFalse(TypeUtils.isCharType(" "));
    assertFalse(TypeUtils.isCharType("ABC"));
  }

  @Test
  public void isByteType() {
    assertTrue(TypeUtils.isByteType("B"));

    assertFalse(TypeUtils.isByteType(""));
    assertFalse(TypeUtils.isByteType(" "));
    assertFalse(TypeUtils.isByteType("ABC"));
  }

  @Test
  public void isShortType() {
    assertTrue(TypeUtils.isShortType("S"));

    assertFalse(TypeUtils.isShortType(""));
    assertFalse(TypeUtils.isShortType(" "));
    assertFalse(TypeUtils.isShortType("ABC"));
  }

  @Test
  public void isIntType() {
    assertTrue(TypeUtils.isIntType("I"));

    assertFalse(TypeUtils.isIntType(""));
    assertFalse(TypeUtils.isIntType(" "));
    assertFalse(TypeUtils.isIntType("ABC"));
  }

  @Test
  public void isFloatType() {
    assertTrue(TypeUtils.isFloatType("F"));

    assertFalse(TypeUtils.isFloatType(""));
    assertFalse(TypeUtils.isFloatType(" "));
    assertFalse(TypeUtils.isFloatType("ABC"));
  }

  @Test
  public void isLongType() {
    assertTrue(TypeUtils.isLongType("J"));

    assertFalse(TypeUtils.isLongType(""));
    assertFalse(TypeUtils.isLongType(" "));
    assertFalse(TypeUtils.isLongType("ABC"));
  }

  @Test
  public void isDoubleType() {
    assertTrue(TypeUtils.isDoubleType("D"));

    assertFalse(TypeUtils.isDoubleType(""));
    assertFalse(TypeUtils.isDoubleType(" "));
    assertFalse(TypeUtils.isDoubleType("ABC"));
  }

  @Test
  public void isArrayType() {
    assertTrue(TypeUtils.isArrayType("[Z"));
    assertTrue(TypeUtils.isArrayType("[[Z"));
    assertTrue(TypeUtils.isArrayType("[[[Z"));
    assertTrue(TypeUtils.isArrayType("[Ljava/lang/String;"));
    assertTrue(TypeUtils.isArrayType("[[Ljava/lang/String;"));
    assertTrue(TypeUtils.isArrayType("[[[Ljava/lang/String;"));

    assertFalse(TypeUtils.isArrayType(null));
    assertFalse(TypeUtils.isArrayType(""));
    assertFalse(TypeUtils.isArrayType(" "));
    assertFalse(TypeUtils.isArrayType("Z"));
    assertFalse(TypeUtils.isArrayType("ABC"));
  }

  @Test
  public void getArrayDimension() {
    assertEquals(0, TypeUtils.getArrayDimension(null));
    assertEquals(0, TypeUtils.getArrayDimension(""));
    assertEquals(0, TypeUtils.getArrayDimension(" "));
    assertEquals(0, TypeUtils.getArrayDimension("Z"));
    assertEquals(0, TypeUtils.getArrayDimension("B"));
    assertEquals(0, TypeUtils.getArrayDimension("ABC"));
    assertEquals(0, TypeUtils.getArrayDimension("Ljava/lang/String;"));

    assertEquals(1, TypeUtils.getArrayDimension("[Z"));
    assertEquals(2, TypeUtils.getArrayDimension("[[Z"));
    assertEquals(3, TypeUtils.getArrayDimension("[[[Z"));
    assertEquals(1, TypeUtils.getArrayDimension("[Ljava/lang/String;"));
    assertEquals(2, TypeUtils.getArrayDimension("[[Ljava/lang/String;"));
    assertEquals(3, TypeUtils.getArrayDimension("[[[Ljava/lang/String;"));
  }

  @Test
  public void testGetClassByNull() {
    assertNull(TypeUtils.getClass(null));
    assertNull(TypeUtils.getClass(""));
  }

  @Test
  public void testGetClassByPrimitiveType() {
    assertEquals(Boolean.TYPE, TypeUtils.getClass("Z"));
    assertEquals(Character.TYPE, TypeUtils.getClass("C"));
    assertEquals(Byte.TYPE, TypeUtils.getClass("B"));
    assertEquals(Short.TYPE, TypeUtils.getClass("S"));
    assertEquals(Integer.TYPE, TypeUtils.getClass("I"));
    assertEquals(Float.TYPE, TypeUtils.getClass("F"));
    assertEquals(Long.TYPE, TypeUtils.getClass("J"));
    assertEquals(Double.TYPE, TypeUtils.getClass("D"));
  }

  @Test
  public void testGetClassByPrimitiveWrapType() {
    assertEquals(Boolean.class, TypeUtils.getClass("Ljava/lang/Boolean;"));
    assertEquals(Character.class, TypeUtils.getClass("Ljava/lang/Character;"));
    assertEquals(Byte.class, TypeUtils.getClass("Ljava/lang/Byte;"));
    assertEquals(Short.class, TypeUtils.getClass("Ljava/lang/Short;"));
    assertEquals(Integer.class, TypeUtils.getClass("Ljava/lang/Integer;"));
    assertEquals(Float.class, TypeUtils.getClass("Ljava/lang/Float;"));
    assertEquals(Long.class, TypeUtils.getClass("Ljava/lang/Long;"));
    assertEquals(Double.class, TypeUtils.getClass("Ljava/lang/Double;"));
  }

  @Test
  public void testGetClassByReferenceType() {
    assertEquals(List.class, TypeUtils.getClass("Ljava/util/List;"));
    assertEquals(Set.class, TypeUtils.getClass("Ljava/util/Set;"));
    assertEquals(Map.class, TypeUtils.getClass("Ljava/util/Map;"));

    assertEquals(ArrayList.class, TypeUtils.getClass("Ljava/util/ArrayList;"));
    assertEquals(HashSet.class, TypeUtils.getClass("Ljava/util/HashSet;"));
    assertEquals(HashMap.class, TypeUtils.getClass("Ljava/util/HashMap;"));
  }

  @Test
  public void getOriginalComponentType() {
    assertEquals(String.class, TypeUtils.getOriginalComponentType(String.class));
    assertEquals(Integer.class, TypeUtils.getOriginalComponentType(Integer.class));

    assertEquals(String.class, TypeUtils.getOriginalComponentType(String[].class));
    assertEquals(Integer.class, TypeUtils.getOriginalComponentType(Integer[].class));

    assertEquals(String.class, TypeUtils.getOriginalComponentType(String[][].class));
    assertEquals(Integer.class, TypeUtils.getOriginalComponentType(Integer[][].class));

    assertEquals(String.class, TypeUtils.getOriginalComponentType(String[][][].class));
    assertEquals(Integer.class, TypeUtils.getOriginalComponentType(Integer[][][].class));
  }

  @Test
  public void getTypeDescriptorByPrimitiveType() {
    assertEquals("Z", TypeUtils.getTypeDescriptor(Boolean.TYPE));
    assertEquals("C", TypeUtils.getTypeDescriptor(Character.TYPE));
    assertEquals("B", TypeUtils.getTypeDescriptor(Byte.TYPE));
    assertEquals("S", TypeUtils.getTypeDescriptor(Short.TYPE));
    assertEquals("I", TypeUtils.getTypeDescriptor(Integer.TYPE));
    assertEquals("F", TypeUtils.getTypeDescriptor(Float.TYPE));
    assertEquals("J", TypeUtils.getTypeDescriptor(Long.TYPE));
    assertEquals("D", TypeUtils.getTypeDescriptor(Double.TYPE));
  }

  @Test
  public void getTypeDescriptorByPrimitiveArrayType() {
    assertEquals("[Z", TypeUtils.getTypeDescriptor(boolean[].class));
    assertEquals("[C", TypeUtils.getTypeDescriptor(char[].class));
    assertEquals("[B", TypeUtils.getTypeDescriptor(byte[].class));
    assertEquals("[S", TypeUtils.getTypeDescriptor(short[].class));
    assertEquals("[I", TypeUtils.getTypeDescriptor(int[].class));
    assertEquals("[F", TypeUtils.getTypeDescriptor(float[].class));
    assertEquals("[J", TypeUtils.getTypeDescriptor(long[].class));
    assertEquals("[D", TypeUtils.getTypeDescriptor(double[].class));
  }

  @Test
  public void getTypeDescriptorByPrimitiveDimensionArrayType() {
    assertEquals("[[Z", TypeUtils.getTypeDescriptor(boolean[][].class));
    assertEquals("[[C", TypeUtils.getTypeDescriptor(char[][].class));
    assertEquals("[[B", TypeUtils.getTypeDescriptor(byte[][].class));
    assertEquals("[[S", TypeUtils.getTypeDescriptor(short[][].class));
    assertEquals("[[I", TypeUtils.getTypeDescriptor(int[][].class));
    assertEquals("[[F", TypeUtils.getTypeDescriptor(float[][].class));
    assertEquals("[[J", TypeUtils.getTypeDescriptor(long[][].class));
    assertEquals("[[D", TypeUtils.getTypeDescriptor(double[][].class));
  }

  @Test
  public void getTypeDescriptorByPrimitiveWrapType() {
    assertEquals("Ljava.lang.Boolean;", TypeUtils.getTypeDescriptor(Boolean.class));
    assertEquals("Ljava.lang.Character;", TypeUtils.getTypeDescriptor(Character.class));
    assertEquals("Ljava.lang.Byte;", TypeUtils.getTypeDescriptor(Byte.class));
    assertEquals("Ljava.lang.Short;", TypeUtils.getTypeDescriptor(Short.class));
    assertEquals("Ljava.lang.Integer;", TypeUtils.getTypeDescriptor(Integer.class));
    assertEquals("Ljava.lang.Float;", TypeUtils.getTypeDescriptor(Float.class));
    assertEquals("Ljava.lang.Long;", TypeUtils.getTypeDescriptor(Long.class));
    assertEquals("Ljava.lang.Double;", TypeUtils.getTypeDescriptor(Double.class));
  }

  @Test
  public void getTypeDescriptorByPrimitiveWrapArrayType() {
    assertEquals("[Ljava.lang.Boolean;", TypeUtils.getTypeDescriptor(Boolean[].class));
    assertEquals("[Ljava.lang.Character;", TypeUtils.getTypeDescriptor(Character[].class));
    assertEquals("[Ljava.lang.Byte;", TypeUtils.getTypeDescriptor(Byte[].class));
    assertEquals("[Ljava.lang.Short;", TypeUtils.getTypeDescriptor(Short[].class));
    assertEquals("[Ljava.lang.Integer;", TypeUtils.getTypeDescriptor(Integer[].class));
    assertEquals("[Ljava.lang.Float;", TypeUtils.getTypeDescriptor(Float[].class));
    assertEquals("[Ljava.lang.Long;", TypeUtils.getTypeDescriptor(Long[].class));
    assertEquals("[Ljava.lang.Double;", TypeUtils.getTypeDescriptor(Double[].class));
  }

  @Test
  public void getTypeDescriptorByPrimitiveWrapDimensionArrayType() {
    assertEquals("[[Ljava.lang.Boolean;", TypeUtils.getTypeDescriptor(Boolean[][].class));
    assertEquals("[[Ljava.lang.Character;", TypeUtils.getTypeDescriptor(Character[][].class));
    assertEquals("[[Ljava.lang.Byte;", TypeUtils.getTypeDescriptor(Byte[][].class));
    assertEquals("[[Ljava.lang.Short;", TypeUtils.getTypeDescriptor(Short[][].class));
    assertEquals("[[Ljava.lang.Integer;", TypeUtils.getTypeDescriptor(Integer[][].class));
    assertEquals("[[Ljava.lang.Float;", TypeUtils.getTypeDescriptor(Float[][].class));
    assertEquals("[[Ljava.lang.Long;", TypeUtils.getTypeDescriptor(Long[][].class));
    assertEquals("[[Ljava.lang.Double;", TypeUtils.getTypeDescriptor(Double[][].class));
  }

  @Test
  public void getTypeDescriptorByParameterizedType() {
    assertEquals("Ljava.util.List<Ljava.lang.String;>;", TypeUtils.getTypeDescriptor(ParameterizedTypeImpl.make(List.class, new Type[]{String.class}, null)));
    assertEquals("Ljava.util.List<Ljava.util.List<Ljava.lang.String;>;>;", TypeUtils.getTypeDescriptor(ParameterizedTypeImpl.make(List.class,
        new Type[]{ParameterizedTypeImpl.make(List.class, new Type[]{String.class}, null)}, null)));

    assertEquals("Ljava.util.Map<Ljava.lang.Long;Ljava.lang.String;>;", TypeUtils.getTypeDescriptor(ParameterizedTypeImpl.make(Map.class, new Type[]{Long.class, String.class}, null)));
    assertEquals("Ljava.util.Map<Ljava.lang.Long;Ljava.util.List<Ljava.lang.String;>;>;", TypeUtils.getTypeDescriptor(
        ParameterizedTypeImpl.make(Map.class, new Type[]{Long.class, ParameterizedTypeImpl.make(List.class, new Type[]{String.class}, null)}, null))
    );
  }
}