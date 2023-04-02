package com.example.demo.validator.annotation;

import com.example.demo.validator.impl.UniqueInList;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueInList.class)
public @interface ValidUniqueInList {

    String message() default "msg.error.generic.duplicate.values";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
