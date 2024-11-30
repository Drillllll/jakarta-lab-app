package com.demo.rest.authorization.interceptor.biding;


import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows any of specified roles.
 */
@InterceptorBinding
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AllowRoles {

    /**
     * @return array of security roles
     */
    @Nonbinding//Required so interceptor does not need to define exact attribute values.
            String[] value() default {};

}
