package com.softeer.config;

import com.softeer.entity.Role;
import io.swagger.v3.oas.annotations.Hidden;
import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Hidden
public @interface LoginUserId {
    Role role() default Role.NORMAL;
}
