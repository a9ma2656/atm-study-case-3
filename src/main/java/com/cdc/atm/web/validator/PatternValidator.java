package com.cdc.atm.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;

public class PatternValidator implements ConstraintValidator<Pattern, String> {
    String regex;

    @Override
    public void initialize(Pattern constraintAnnotation) {
        this.regex = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s != null && !"".equals(s.trim()) && regex != null) {
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
            Matcher m = p.matcher(s);
            return m.matches();
        }
        return true;
    }
}
