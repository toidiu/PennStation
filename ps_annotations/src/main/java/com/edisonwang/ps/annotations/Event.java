package com.edisonwang.ps.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author edi
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.CLASS)
@Inherited
public @interface Event {
    String postFix() default "";

    Class base() default Default.class;

    ParcelableField[] fields() default {};

    boolean success() default true;
}
