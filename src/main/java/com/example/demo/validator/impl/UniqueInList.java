package com.example.demo.validator.impl;

import com.example.demo.validator.annotation.ValidUniqueInList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;

public class UniqueInList implements ConstraintValidator<ValidUniqueInList, List<Long>> {

    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.size() == new HashSet<>(value).size();
    }

}