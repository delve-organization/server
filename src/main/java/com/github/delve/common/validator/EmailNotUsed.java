package com.github.delve.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = EmailNotUsedValidator.class)
@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface EmailNotUsed {
    String message() default "validation.constraints.emailnotused";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
