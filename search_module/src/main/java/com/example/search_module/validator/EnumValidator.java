package com.example.search_module.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private EnumValid annotation;
    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        // 빈값으로 들어오면 기본값으로 설정하기 위함
        if (enumValues == null || value == null) return true;
        for (Object enumValue: enumValues) {
            if(value.equalsIgnoreCase(enumValue.toString())) return true;
        }
        return false;
    }
}
