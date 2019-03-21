package com.example.jtademo.validProcess;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class CheckTimeRangValidator implements ConstraintValidator<CheckTimeRang, Object> {

    private String startTime;

    private String endTime;

    @Override
    public void initialize(CheckTimeRang constraintAnnotation) {
        startTime = constraintAnnotation.startTime();

        endTime = constraintAnnotation.endTime();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return true;
        }

        BeanWrapper wrapper = new BeanWrapperImpl(value);

        Object start = wrapper.getPropertyValue(startTime);
        Object end = wrapper.getPropertyValue(endTime);

        if (null == start) {
            return false;
        } else if (null == end) {
            return true;
        }

        return ((Date) start).compareTo((Date) end) <= 0;
    }
}
