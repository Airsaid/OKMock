package com.airsaid.okmock;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import static org.junit.Assert.assertEquals;

/**
 * @author airsaid
 */
public class TypeUtilsTest {

  @Test
  public void getReferenceTypeDescriptor() {
    assertEquals("Ljava/lang/String;", TypeUtils.getReferenceTypeDescriptor("Ljava/lang/String;"));
    assertEquals("Ljava/lang/List;", TypeUtils.getReferenceTypeDescriptor("Ljava/lang/List;"));

    assertEquals("[Ljava/lang/String;", TypeUtils.getReferenceTypeDescriptor("[Ljava/lang/String;"));
    assertEquals("[Ljava/lang/List;", TypeUtils.getReferenceTypeDescriptor("[Ljava/lang/List;"));

    assertEquals("[[Ljava/lang/String;", TypeUtils.getReferenceTypeDescriptor("[[Ljava/lang/String;"));
    assertEquals("[[Ljava/lang/List;", TypeUtils.getReferenceTypeDescriptor("[[Ljava/lang/List;"));
  }

  @Test
  public void getReferenceTypeDescriptorByPrimitiveType() {
    assertEquals("Ljava/lang/Boolean;", TypeUtils.getReferenceTypeDescriptor("Z"));
    assertEquals("Ljava/lang/Character;", TypeUtils.getReferenceTypeDescriptor("C"));
    assertEquals("Ljava/lang/Byte;", TypeUtils.getReferenceTypeDescriptor("B"));
    assertEquals("Ljava/lang/Short;", TypeUtils.getReferenceTypeDescriptor("S"));
    assertEquals("Ljava/lang/Integer;", TypeUtils.getReferenceTypeDescriptor("I"));
    assertEquals("Ljava/lang/Float;", TypeUtils.getReferenceTypeDescriptor("F"));
    assertEquals("Ljava/lang/Long;", TypeUtils.getReferenceTypeDescriptor("J"));
    assertEquals("Ljava/lang/Double;", TypeUtils.getReferenceTypeDescriptor("D"));
  }

  @Test
  public void getReferenceTypeDescriptorByPrimitiveArrayType() {
    assertEquals("[Ljava/lang/Boolean;", TypeUtils.getReferenceTypeDescriptor("[Z"));
    assertEquals("[Ljava/lang/Character;", TypeUtils.getReferenceTypeDescriptor("[C"));
    assertEquals("[Ljava/lang/Byte;", TypeUtils.getReferenceTypeDescriptor("[B"));
    assertEquals("[Ljava/lang/Short;", TypeUtils.getReferenceTypeDescriptor("[S"));
    assertEquals("[Ljava/lang/Integer;", TypeUtils.getReferenceTypeDescriptor("[I"));
    assertEquals("[Ljava/lang/Float;", TypeUtils.getReferenceTypeDescriptor("[F"));
    assertEquals("[Ljava/lang/Long;", TypeUtils.getReferenceTypeDescriptor("[J"));
    assertEquals("[Ljava/lang/Double;", TypeUtils.getReferenceTypeDescriptor("[D"));
  }

  @Test
  public void getReferenceTypeDescriptorByPrimitiveDimensionArrayType() {
    assertEquals("[[Ljava/lang/Boolean;", TypeUtils.getReferenceTypeDescriptor("[[Z"));
    assertEquals("[[Ljava/lang/Character;", TypeUtils.getReferenceTypeDescriptor("[[C"));
    assertEquals("[[Ljava/lang/Byte;", TypeUtils.getReferenceTypeDescriptor("[[B"));
    assertEquals("[[Ljava/lang/Short;", TypeUtils.getReferenceTypeDescriptor("[[S"));
    assertEquals("[[Ljava/lang/Integer;", TypeUtils.getReferenceTypeDescriptor("[[I"));
    assertEquals("[[Ljava/lang/Float;", TypeUtils.getReferenceTypeDescriptor("[[F"));
    assertEquals("[[Ljava/lang/Long;", TypeUtils.getReferenceTypeDescriptor("[[J"));
    assertEquals("[[Ljava/lang/Double;", TypeUtils.getReferenceTypeDescriptor("[[D"));
  }

  @Test
  public void getReferenceTypeDescriptorByPrimitiveWrapType() {
    assertEquals("Ljava/lang/Boolean;", TypeUtils.getReferenceTypeDescriptor("Ljava/lang/Boolean;"));
    assertEquals("Ljava/lang/Character;", TypeUtils.getReferenceTypeDescriptor("Ljava/lang/Character;"));
    assertEquals("Ljava/lang/Byte;", TypeUtils.getReferenceTypeDescriptor("Ljava/lang/Byte;"));
    assertEquals("Ljava/lang/Short;", TypeUtils.getReferenceTypeDescriptor("Ljava/lang/Short;"));
    assertEquals("Ljava/lang/Integer;", TypeUtils.getReferenceTypeDescriptor("Ljava/lang/Integer;"));
    assertEquals("Ljava/lang/Float;", TypeUtils.getReferenceTypeDescriptor("Ljava/lang/Float;"));
    assertEquals("Ljava/lang/Long;", TypeUtils.getReferenceTypeDescriptor("Ljava/lang/Long;"));
    assertEquals("Ljava/lang/Double;", TypeUtils.getReferenceTypeDescriptor("Ljava/lang/Double;"));
  }

  @Test
  public void getReferenceTypeDescriptorByPrimitiveWrapArrayType() {
    assertEquals("[Ljava/lang/Boolean;", TypeUtils.getReferenceTypeDescriptor("[Ljava/lang/Boolean;"));
    assertEquals("[Ljava/lang/Character;", TypeUtils.getReferenceTypeDescriptor("[Ljava/lang/Character;"));
    assertEquals("[Ljava/lang/Byte;", TypeUtils.getReferenceTypeDescriptor("[Ljava/lang/Byte;"));
    assertEquals("[Ljava/lang/Short;", TypeUtils.getReferenceTypeDescriptor("[Ljava/lang/Short;"));
    assertEquals("[Ljava/lang/Integer;", TypeUtils.getReferenceTypeDescriptor("[Ljava/lang/Integer;"));
    assertEquals("[Ljava/lang/Float;", TypeUtils.getReferenceTypeDescriptor("[Ljava/lang/Float;"));
    assertEquals("[Ljava/lang/Long;", TypeUtils.getReferenceTypeDescriptor("[Ljava/lang/Long;"));
    assertEquals("[Ljava/lang/Double;", TypeUtils.getReferenceTypeDescriptor("[Ljava/lang/Double;"));
  }

  @Test
  public void getReferenceTypeDescriptorByPrimitiveWrapDimensionArrayType() {
    assertEquals("[[Ljava/lang/Boolean;", TypeUtils.getReferenceTypeDescriptor("[[Ljava/lang/Boolean;"));
    assertEquals("[[Ljava/lang/Character;", TypeUtils.getReferenceTypeDescriptor("[[Ljava/lang/Character;"));
    assertEquals("[[Ljava/lang/Byte;", TypeUtils.getReferenceTypeDescriptor("[[Ljava/lang/Byte;"));
    assertEquals("[[Ljava/lang/Short;", TypeUtils.getReferenceTypeDescriptor("[[Ljava/lang/Short;"));
    assertEquals("[[Ljava/lang/Integer;", TypeUtils.getReferenceTypeDescriptor("[[Ljava/lang/Integer;"));
    assertEquals("[[Ljava/lang/Float;", TypeUtils.getReferenceTypeDescriptor("[[Ljava/lang/Float;"));
    assertEquals("[[Ljava/lang/Long;", TypeUtils.getReferenceTypeDescriptor("[[Ljava/lang/Long;"));
    assertEquals("[[Ljava/lang/Double;", TypeUtils.getReferenceTypeDescriptor("[[Ljava/lang/Double;"));
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