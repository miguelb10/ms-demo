package com.example.demo.validator.impl;

import com.example.demo.validator.annotation.ValidEachEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EachEnumValidator implements ConstraintValidator<ValidEachEnum, List<String>> {

    private Class<? extends Enum> enumClass;

    @Override
    public void initialize(ValidEachEnum constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(List<String> enums, ConstraintValidatorContext constraintValidatorContext) {
        if (enums == null) {
            return true;
        }
        for (String value : enums) {
            if (value != null) {
                for (Enum<?> enumValue : enumClass.getEnumConstants()) {
                    if (enumValue.name().equalsIgnoreCase(value)) {
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }
}
