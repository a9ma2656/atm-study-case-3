package com.cdc.atm.web.validator;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinValidator implements ConstraintValidator<Min, String> {

    private String value;

    @Override
    public void initialize(Min constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s != null && !"".equals(s.trim())) {
            try {
                BigDecimal amount = BigDecimal.valueOf(Integer.parseInt(s.trim()));
                if (value != null && !"".equals(value)) {
                    BigDecimal minAmount = BigDecimal.valueOf(Integer.parseInt(value));
                    if (amount.compareTo(minAmount) == -1) {
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
