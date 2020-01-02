package com.cdc.atm.web.validator;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumericValidator implements ConstraintValidator<Numeric, String> {
    private String multiply;

    @Override
    public void initialize(Numeric constraintAnnotation) {
        this.multiply = constraintAnnotation.multiply();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s != null && !"".equals(s.trim())) {
            try {
                BigDecimal amount = BigDecimal.valueOf(Integer.parseInt(s.trim()));
                if (multiply != null && !"".equals(multiply)) {
                    MathContext mc = new MathContext(2);
                    BigDecimal divideAmount = amount.remainder(new BigDecimal(multiply), mc);
                    if (divideAmount.compareTo(new BigDecimal(0)) == 1) {
                        return false;
                    }
                }
                return amount.compareTo(new BigDecimal(0)) == 1;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
