package com.airsaid.okmock.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author airsaid
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface MockValue {

  boolean[] booleanValues() default {};

  char[] charValues() default {};

  byte[] byteValues() default {};

  short[] shortValues() default {};

  int[] intValues() default {};

  float[] floatValues() default {};

  long[] longValues() default {};

  double[] doubleValues() default {};

  String[] stringValues() default {};
}