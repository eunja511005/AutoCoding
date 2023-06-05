package com.eun.tutorial.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SetUserTimeZoneAndFormat {
    // 어노테이션의 추가적인 속성 정의 가능
}

