package com.sipios.refactoring.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = OneOffValidator.class)
public @interface OneOff {
    String[] value();

    String message() default "{com.sipios.constraints.oneoff}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
