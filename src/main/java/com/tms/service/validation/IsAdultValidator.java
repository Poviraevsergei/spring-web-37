package com.tms.service.validation;

import com.tms.annotation.IsAdult;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsAdultValidator implements ConstraintValidator<IsAdult, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value >= 18 && value <= 110;
    }
}
