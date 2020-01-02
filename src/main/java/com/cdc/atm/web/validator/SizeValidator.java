package com.cdc.atm.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SizeValidator implements ConstraintValidator<Size, String> {
    int min;
    int max;

    @Override
    public void initialize(Size constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s != null && !"".equals(s.trim())) {
            int val = s.length();
            if (min < val || max > val) {
                return false;
            }
        }
        return true;
    }
}
