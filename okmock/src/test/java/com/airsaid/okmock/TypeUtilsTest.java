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
    // primitive type
    assertEquals("Z", TypeUtils.getTypeDescriptor(Boolean.TYPE));
    assertEquals("C", TypeUtils.getTypeDescriptor(Character.TYPE));
    assertEquals("B", TypeUtils.getTypeDescriptor(Byte.TYPE));
    assertEquals("S", TypeUtils.getTypeDescriptor(Short.TYPE));
    assertEquals("I", TypeUtils.getTypeDescriptor(Integer.TYPE));
    assertEquals("F", TypeUtils.getTypeDescriptor(Float.TYPE));
    assertEquals("J", TypeUtils.getTypeDescriptor(Long.TYPE));
    assertEquals("D", TypeUtils.getTypeDescriptor(Double.TYPE));

    // primitive array type
    assertEquals("[Z", TypeUtils.getTypeDescriptor(boolean[].class));
    assertEquals("[C", TypeUtils.getTypeDescriptor(char[].class));
    assertEquals("[B", TypeUtils.getTypeDescriptor(byte[].class));
    assertEquals("[S", TypeUtils.getTypeDescriptor(short[].class));
    assertEquals("[I", TypeUtils.getTypeDescriptor(int[].class));
    assertEquals("[F", TypeUtils.getTypeDescriptor(float[].class));
    assertEquals("[J", TypeUtils.getTypeDescriptor(long[].class));
    assertEquals("[D", TypeUtils.getTypeDescriptor(double[].class));

    assertEquals("[[Z", TypeUtils.getTypeDescriptor(boolean[][].class));
    assertEquals("[[C", TypeUtils.getTypeDescriptor(char[][].class));
    assertEquals("[[B", TypeUtils.getTypeDescriptor(byte[][].class));
    assertEquals("[[S", TypeUtils.getTypeDescriptor(short[][].class));
    assertEquals("[[I", TypeUtils.getTypeDescriptor(int[][].class));
    assertEquals("[[F", TypeUtils.getTypeDescriptor(float[][].class));
    assertEquals("[[J", TypeUtils.getTypeDescriptor(long[][].class));
    assertEquals("[[D", TypeUtils.getTypeDescriptor(double[][].class));

    // primitive wrap type
    assertEquals("Ljava.lang.Boolean;", TypeUtils.getTypeDescriptor(Boolean.class));
    assertEquals("Ljava.lang.Character;", TypeUtils.getTypeDescriptor(Character.class));
    assertEquals("Ljava.lang.Byte;", TypeUtils.getTypeDescriptor(Byte.class));
    assertEquals("Ljava.lang.Short;", TypeUtils.getTypeDescriptor(Short.class));
    assertEquals("Ljava.lang.Integer;", TypeUtils.getTypeDescriptor(Integer.class));
    assertEquals("Ljava.lang.Float;", TypeUtils.getTypeDescriptor(Float.class));
    assertEquals("Ljava.lang.Long;", TypeUtils.getTypeDescriptor(Long.class));
    assertEquals("Ljava.lang.Double;", TypeUtils.getTypeDescriptor(Double.class));

    // primitive wrap array type
    assertEquals("[Ljava.lang.Boolean;", TypeUtils.getTypeDescriptor(Boolean[].class));
    assertEquals("[Ljava.lang.Character;", TypeUtils.getTypeDescriptor(Character[].class));
    assertEquals("[Ljava.lang.Byte;", TypeUtils.getTypeDescriptor(Byte[].class));
    assertEquals("[Ljava.lang.Short;", TypeUtils.getTypeDescriptor(Short[].class));
    assertEquals("[Ljava.lang.Integer;", TypeUtils.getTypeDescriptor(Integer[].class));
    assertEquals("[Ljava.lang.Float;", TypeUtils.getTypeDescriptor(Float[].class));
    assertEquals("[Ljava.lang.Long;", TypeUtils.getTypeDescriptor(Long[].class));
    assertEquals("[Ljava.lang.Double;", TypeUtils.getTypeDescriptor(Double[].class));

    assertEquals("[[Ljava.lang.Boolean;", TypeUtils.getTypeDescriptor(Boolean[][].class));
    assertEquals("[[Ljava.lang.Character;", TypeUtils.getTypeDescriptor(Character[][].class));
    assertEquals("[[Ljava.lang.Byte;", TypeUtils.getTypeDescriptor(Byte[][].class));
    assertEquals("[[Ljava.lang.Short;", TypeUtils.getTypeDescriptor(Short[][].class));
    assertEquals("[[Ljava.lang.Integer;", TypeUtils.getTypeDescriptor(Integer[][].class));
    assertEquals("[[Ljava.lang.Float;", TypeUtils.getTypeDescriptor(Float[][].class));
    assertEquals("[[Ljava.lang.Long;", TypeUtils.getTypeDescriptor(Long[][].class));
    assertEquals("[[Ljava.lang.Double;", TypeUtils.getTypeDescriptor(Double[][].class));

    // list type
    assertEquals("Ljava.util.List<Ljava.lang.String;>;", TypeUtils.getTypeDescriptor(ParameterizedTypeImpl.make(List.class, new Type[]{String.class}, null)));
    assertEquals("Ljava.util.List<Ljava.util.List<Ljava.lang.String;>;>;", TypeUtils.getTypeDescriptor(ParameterizedTypeImpl.make(List.class,
        new Type[]{ParameterizedTypeImpl.make(List.class, new Type[]{String.class}, null)}, null)));

    // map type
    assertEquals("Ljava.util.Map<Ljava.lang.Long;Ljava.lang.String;>;", TypeUtils.getTypeDescriptor(ParameterizedTypeImpl.make(Map.class, new Type[]{Long.class, String.class}, null)));
    assertEquals("Ljava.util.Map<Ljava.lang.Long;Ljava.util.List<Ljava.lang.String;>;>;", TypeUtils.getTypeDescriptor(
        ParameterizedTypeImpl.make(Map.class, new Type[]{Long.class, ParameterizedTypeImpl.make(List.class, new Type[]{String.class}, null)}, null))
    );
  }
}