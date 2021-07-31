package com.airsaid.okmock.api;

import java.lang.reflect.Type;

/**
 * @author airsaid
 */
public class Types {

  public static boolean isBoolean(Type type) {
    return "boolean".equals(type.toString()) || "java.lang.Boolean".equals(type.toString());
  }

  public static boolean isCharacter(Type type) {
    return "char".equals(type.toString()) || "java.lang.Character".equals(type.toString());
  }

  public static boolean isByte(Type type) {
    return "byte".equals(type.toString()) || "java.lang.Byte".equals(type.toString());
  }

  public static boolean isShort(Type type) {
    return "short".equals(type.toString()) || "java.lang.Short".equals(type.toString());
  }

  public static boolean isInteger(Type type) {
    return "int".equals(type.toString()) || "java.lang.Integer".equals(type.toString());
  }

  public static boolean isFloat(Type type) {
    return "float".equals(type.toString()) || "java.lang.Float".equals(type.toString());
  }

  public static boolean isLong(Type type) {
    return "long".equals(type.toString()) || "java.lang.Long".equals(type.toString());
  }

  public static boolean isDouble(Type type) {
    return "double".equals(type.toString()) || "java.lang.Double".equals(type.toString());
  }

  public static Boolean isString(Type type) {
    if (type instanceof Class<?>) {
      return "java.lang.String".equals(((Class<?>) type).getName());
    }
    return false;
  }
}
