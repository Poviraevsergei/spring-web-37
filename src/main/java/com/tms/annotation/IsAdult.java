package com.tms.annotation;

import com.tms.service.validation.IsAdultValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = IsAdultValidator.class)
public @interface IsAdult {
    String message() default "Should more/equal than 18 and less than 110";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
