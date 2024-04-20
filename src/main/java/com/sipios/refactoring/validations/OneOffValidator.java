package com.sipios.refactoring.validations;

import com.sipios.refactoring.dtos.Purchase;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class OneOffValidator implements ConstraintValidator<OneOff, String> {


    String[] permittedValue;

    @Override
    public void initialize(OneOff constraintAnnotation) {
        permittedValue = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {


        return Arrays.stream(permittedValue).anyMatch(e -> e.equals(s));
    }
}
