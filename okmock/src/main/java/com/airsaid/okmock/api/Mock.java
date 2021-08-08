package com.airsaid.okmock.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is an identification annotation. that is used to decorate field.
 * The field to be decorated must not have an assignment operation.
 * <p>
 * The decorated fields are automatically populated with random data.
 *
 * @author airsaid
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Mock {

}