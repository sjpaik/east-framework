package com.east.framework.core.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Data Binding을 위한 Annotation.
 * 
 * Updated on : 2015-11-11 Updated by : love.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BindData {

    /**
     * 바인딩 변수 이름.
     * 
     * @return String
     */
    String value();

}
