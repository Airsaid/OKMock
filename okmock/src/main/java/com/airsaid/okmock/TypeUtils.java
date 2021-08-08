package com.airsaid.okmock;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This class can be used to make it easier to manipulate type descriptors.
 *
 * @author airsaid
 */
class TypeUtils {

  /**
   * Returns the class of the given field descriptor.
   * <p>
   * For example, if the field descriptor is {@code Z},
   * this method returns {@link Boolean#TYPE} class.
   * <p>
   * if the field descriptor is {@code Ljava/lang/String;},
   * this method returns {@link String} class.
   * <p>
   * if the field descriptor is {@code [Ljava/lang/String;},
   * this method returns {@code String[]} class.
   *
   * @param descriptor the field descriptor.
   * @return the class corresponding to the given field descriptor.
   */
  public static Class<?> getClass(String descriptor) {
    if (!checkDescriptor(descriptor)) {
      return null;
    }

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

    boolean isArray = isArrayType(descriptor);
    int arrayDimension = getArrayDimension(descriptor);
    String className = getClassName(descriptor);

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

  /**
   * Returns whether the given field descriptor is a primitive type.
   *
   * @param descriptor the field descriptor.
   * @return return {@literal true} if it is a primitive type, {@literal false} otherwise.
   */
  public static boolean isPrimitiveType(String descriptor) {
    return isBooleanType(descriptor) || isCharType(descriptor) || isByteType(descriptor) || isShortType(descriptor)
        || isIntType(descriptor) || isFloatType(descriptor) || isLongType(descriptor) || isDoubleType(descriptor);
  }

  /**
   * Returns whether the given field descriptor is a {@code boolean} primitive type.
   *
   * @param descriptor the field descriptor.
   * @return return {@literal true} if it is a {@code boolean} primitive type, {@literal false} otherwise.
   */
  public static boolean isBooleanType(String descriptor) {
    return "Z".equals(descriptor);
  }

  /**
   * Returns whether the given field descriptor is a {@code char} primitive type.
   *
   * @param descriptor the field descriptor.
   * @return return {@literal true} if it is a {@code char} primitive type, {@literal false} otherwise.
   */
  public static boolean isCharType(String descriptor) {
    return "C".equals(descriptor);
  }

  /**
   * Returns whether the given field descriptor is a {@code byte} primitive type.
   *
   * @param descriptor the field descriptor.
   * @return return {@literal true} if it is a {@code byte} primitive type, {@literal false} otherwise.
   */
  public static boolean isByteType(String descriptor) {
    return "B".equals(descriptor);
  }

  /**
   * Returns whether the given field descriptor is a {@code short} primitive type.
   *
   * @param descriptor the field descriptor.
   * @return return {@literal true} if it is a {@code short} primitive type, {@literal false} otherwise.
   */
  public static boolean isShortType(String descriptor) {
    return "S".equals(descriptor);
  }

  /**
   * Returns whether the given field descriptor is a {@code int} primitive type.
   *
   * @param descriptor the field descriptor.
   * @return return {@literal true} if it is a {@code int} primitive type, {@literal false} otherwise.
   */
  public static boolean isIntType(String descriptor) {
    return "I".equals(descriptor);
  }

  /**
   * Returns whether the given field descriptor is a {@code float} primitive type.
   *
   * @param descriptor the field descriptor.
   * @return return {@literal true} if it is a {@code float} primitive type, {@literal false} otherwise.
   */
  public static boolean isFloatType(String descriptor) {
    return "F".equals(descriptor);
  }

  /**
   * Returns whether the given field descriptor is a {@code long} primitive type.
   *
   * @param descriptor the field descriptor.
   * @return return {@literal true} if it is a {@code long} primitive type, {@literal false} otherwise.
   */
  public static boolean isLongType(String descriptor) {
    return "J".equals(descriptor);
  }

  /**
   * Returns whether the given field descriptor is a {@code double} primitive type.
   *
   * @param descriptor the field descriptor.
   * @return return {@literal true} if it is a {@code double} primitive type, {@literal false} otherwise.
   */
  public static boolean isDoubleType(String descriptor) {
    return "D".equals(descriptor);
  }

  /**
   * Returns whether the given field descriptor is a array type.
   *
   * @param descriptor the field descriptor.
   * @return return {@literal true} if it is a array type, {@literal false} otherwise.
   */
  public static boolean isArrayType(String descriptor) {
    return checkDescriptor(descriptor) && '[' == descriptor.charAt(0);
  }

  /**
   * Returns the dimension of the array if the given field descriptor is an array type.
   *
   * @param descriptor the field descriptor.
   * @return return dimension of the array. return 0 if not array type.
   */
  public static int getArrayDimension(String descriptor) {
    return checkDescriptor(descriptor) ? descriptor.lastIndexOf('[') + 1 : 0;
  }

  /**
   * Returns the class name of the given field descriptor.
   * <p>
   * If the field descriptor is a primitive type, an empty string is returned.
   * <p>
   * For example, if the field descriptor is:
   * <p>
   * {@code Ljava/lang/String;}, Then returns:
   * <p>
   * {@code java.lang.String}.
   *
   * @param descriptor field descriptor.
   * @return the class name of the field descriptor.
   */
  public static String getClassName(String descriptor) {
    if (!checkDescriptor(descriptor)) {
      return "";
    }
    // primitive types have no class name
    if (isPrimitiveType(descriptor)) {
      return "";
    }
    String className = descriptor;
    // remove the array identifier of the descriptor
    if (isArrayType(className)) {
      className = className.substring(className.lastIndexOf('[') + 1);
    }
    // remove the class identifier of the descriptor
    if (className.charAt(0) == 'L') {
      className = className.substring(1);
    }
    // remove the end identifier of the descriptor
    if (className.charAt(className.length() - 1) == ';') {
      className = className.substring(0, className.length() - 1);
    }
    // replace delimiter
    return className.replaceAll("/", ".");
  }

  /**
   * Returns the original component type of the given class.
   * <p>
   * For example, if the specified class is {@code String[]},
   * then returns: {@code String} class.
   *
   * @param clazz the specified class.
   * @return the original component type class.
   */
  public static Class<?> getOriginalComponentType(Class<?> clazz) {
    while (clazz != null && clazz.getComponentType() != null) {
      clazz = clazz.getComponentType();
    }
    return clazz;
  }

  /**
   * Returns the corresponding type descriptor through the
   * specified {@link java.lang.reflect.Type} class instance.
   *
   * @param type the {@link java.lang.reflect.Type} class instance.
   * @return the type descriptor of this {@link java.lang.reflect.Type}.
   */
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

  private static boolean checkDescriptor(String descriptor) {
    return descriptor != null && !descriptor.isEmpty();
  }
}