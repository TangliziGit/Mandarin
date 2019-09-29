package org.a3.mandarin.common.annotation;

import org.a3.mandarin.common.enums.PermissionType;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    PermissionType[] value() default PermissionType.TOURIST;
}
