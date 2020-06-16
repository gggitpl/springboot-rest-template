package com.gggitpl.template.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * zk分布式锁
 *
 * @author gggitpl
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZkLock {

    int time() default 0;

    TimeUnit timeunit() default TimeUnit.MILLISECONDS;
}
