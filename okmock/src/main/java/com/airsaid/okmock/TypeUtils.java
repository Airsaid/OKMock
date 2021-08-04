package com.airsaid.okmock;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author airsaid
 */
class TypeUtils {

  public static String getReferenceTypeDescriptor(String descriptor) {
    String basicDescriptor = descriptor;
    if (isArrayType(descriptor)) {
      basicDescriptor = getArrayBasicDescriptor(descriptor);
    }
    if (!isPrimitiveType(basicDescriptor)) {
      return descriptor;
    }

    String prefix = getArrayDescriptorPrefix(descriptor);
    if (isBooleanType(basicDescriptor)) {
      return prefix + "Ljava/lang/Boolean;";
    } else if (isCharType(basicDescriptor)) {
      return prefix + "Ljava/lang/Character;";
    } else if (isByteType(basicDescriptor)) {
      return prefix + "Ljava/lang/Byte;";
    } else if (isShortType(basicDescriptor)) {
      return prefix + "Ljava/lang/Short;";
    } else if (isIntType(basicDescriptor)) {
      return prefix + "Ljava/lang/Integer;";
    } else if (isFloatType(basicDescriptor)) {
      return prefix + "Ljava/lang/Float;";
    } else if (isLongType(basicDescriptor)) {
      return prefix + "Ljava/lang/Long;";
    } else if (isDoubleType(basicDescriptor)) {
      return prefix + "Ljava/lang/Double;";
    }
    throw new IllegalArgumentException("Unknown descriptor: " + descriptor);
  }

  private static String getArrayBasicDescriptor(String descriptor) {
    if (isArrayType(descriptor)) {
      return descriptor.substring(descriptor.lastIndexOf('['));
    }
    return "";
  }

  private static String getArrayDescriptorPrefix(String descriptor) {
    if (isArrayType(descriptor)) {
      return descriptor.substring(0, descriptor.lastIndexOf('['));
    }
    return "";
  }

  public static boolean isPrimitiveType(String descriptor) {
    return isBooleanType(descriptor) || isCharType(descriptor) || isByteType(descriptor) || isShortType(descriptor)
        || isIntType(descriptor) || isFloatType(descriptor) || isLongType(descriptor) || isDoubleType(descriptor);
  }

  public static boolean isBooleanType(String descriptor) {
    return "Z".equals(descriptor);
  }

  public static boolean isCharType(String descriptor) {
    return "C".equals(descriptor);
  }

  public static boolean isByteType(String descriptor) {
    return "B".equals(descriptor);
  }

  public static boolean isShortType(String descriptor) {
    return "S".equals(descriptor);
  }

  public static boolean isIntType(String descriptor) {
    return "I".equals(descriptor);
  }

  public static boolean isFloatType(String descriptor) {
    return "F".equals(descriptor);
  }

  public static boolean isLongType(String descriptor) {
    return "J".equals(descriptor);
  }

  public static boolean isDoubleType(String descriptor) {
    return "D".equals(descriptor);
  }

  public static boolean isArrayType(String descriptor) {
    return descriptor != null && !descriptor.isEmpty() && '[' == descriptor.charAt(0);
  }

  public static String getTypeDescriptor(Type type) {
    if (type instanceof Class<?>) {
      Class<?> clazz = (Class<?>) type;
      if (clazz.isPrimitive()) {
        switch (clazz.getName()) {
          case "boolean":
            return "Z";
          case "char":
            return "C";
          case "byte":
            return "B";
          case "short":
            return "S";
          case "int":
            return "I";
          case "float":
            return "F";
          case "long":
            return "J";
          case "double":
            return "D";
        }
      }
      if (!clazz.isArray()) {
        return "L" + clazz.getName() + ";";
      }
      return clazz.getName();
    } else if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      StringBuilder sb = new StringBuilder();
      Type rawType = parameterizedType.getRawType();
      String rawTypeDescriptor = getTypeDescriptor(rawType);
      sb.append(rawTypeDescriptor, 0, rawTypeDescriptor.length() - 1);
      if (parameterizedType.getActualTypeArguments() != null) {
        for (int i = 0; i < parameterizedType.getActualTypeArguments().length; i++) {
          Type t = parameterizedType.getActualTypeArguments()[i];
          if (i == 0) {
            sb.append("<");
          }
          sb.append(getTypeDescriptor(t));
          if (i == parameterizedType.getActualTypeArguments().length - 1) {
            sb.append(">");
          } else if (';' != sb.charAt(sb.length() - 1)) {
            sb.append(";");
          }
        }
      }
      sb.append(";");
      return sb.toString();
    }
    throw new IllegalArgumentException("Unsupported type: " + type);
  }
}