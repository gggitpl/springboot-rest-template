package com.gggitpl.template.filter;

import java.lang.annotation.*;

/**
 * @author gggitpl
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = FieldFilters.class)
public @interface FieldFiltering {

    Class<?> aClass() default void.class;

    String[] fields() default {};

    boolean keep() default false;
}
