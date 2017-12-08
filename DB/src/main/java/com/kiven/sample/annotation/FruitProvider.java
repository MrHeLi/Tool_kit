package com.kiven.sample.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Kiven on 2017/7/4.
 * Details:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FruitProvider {
    int id() default -1;
    String name() default "";
    String address() default "";
}
