package com.airsaid.okmock;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author airsaid
 */
class TypeUtils {

  public static Class<?> getOriginalComponentType(Class<?> clazz) {
    while (clazz.getComponentType() != null) {
      clazz = clazz.getComponentType();
    }
    return clazz;
  }

  public static Class<?> getClass(String descriptor) {
    if (isPrimitiveType(descriptor)) {
      if (isBooleanType(descriptor)) {
        return Boolean.TYPE;
      } else if (isCharType(descriptor)) {
        return Character.TYPE;
      } else if (isByteType(descriptor)) {
        return Byte.TYPE;
      } else if (isShortType(descriptor)) {
        return Short.TYPE;
      } else if (isIntType(descriptor)) {
        return Integer.TYPE;
      } else if (isFloatType(descriptor)) {
        return Float.TYPE;
      } else if (isLongType(descriptor)) {
        return Long.TYPE;
      } else if (isDoubleType(descriptor)) {
        return Double.TYPE;
      }
    }

    int arrayIndex = descriptor.lastIndexOf('[');
    int arrayDimension = arrayIndex + 1;
    boolean isArray = arrayIndex != -1;
    String className = (isArray ? descriptor.substring(arrayDimension) : descriptor).replaceAll("/", ".");

    try {
      Class<?> clazz = isPrimitiveType(className) ? getClass(className) : Class.forName(className);
      if (isArray) {
        return ArrayUtils.getArray(clazz, arrayDimension, 0, 0).getClass();
      }
      return clazz;
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

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
      return descriptor.substring(descriptor.lastIndexOf('[') + 1);
    }
    return "";
  }

  private static String getArrayDescriptorPrefix(String descriptor) {
    if (isArrayType(descriptor)) {
      return descriptor.substring(0, descriptor.lastIndexOf('[') + 1);
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
    } else if (type instanceof GenericArrayType) {
      GenericArrayType genericArrayType = (GenericArrayType) type;
      Type genericComponentType = genericArrayType.getGenericComponentType();
      return "[" + getTypeDescriptor(genericComponentType);
    }

    throw new IllegalArgumentException("Unsupported type: " + type.getClass());
  }
}