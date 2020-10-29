package com.dev.cinema.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {
    private String field;
    private String fieldMatch;

    public void initialize(FieldsValueMatch constraint) {
        this.field = constraint.field();
        this.fieldMatch = constraint.fieldMatch();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value)
                .getPropertyValue(fieldMatch);
        return fieldValue != null ? fieldValue.equals(fieldMatchValue) : fieldMatchValue == null;
    }
}
