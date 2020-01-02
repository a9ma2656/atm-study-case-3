package com.cdc.atm.web.validator;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxValidator implements ConstraintValidator<Max, String> {

    private String value;

    @Override
    public void initialize(Max constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s != null && !"".equals(s.trim())) {
            try {
                BigDecimal amount = BigDecimal.valueOf(Integer.parseInt(s.trim()));
                if (value != null && !"".equals(value)) {
                    BigDecimal maxAmount = BigDecimal.valueOf(Integer.parseInt(value));
                    if (amount.compareTo(maxAmount) == 1) {
                        return false;
                    }
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return true;
    }
}
