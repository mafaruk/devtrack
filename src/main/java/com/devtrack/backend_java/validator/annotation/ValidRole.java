package com.devtrack.backend_java.validator.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.devtrack.backend_java.validator.StrongPasswordValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
public @interface ValidRole {
    String message() default "Role must be either ADMIN or USER";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
