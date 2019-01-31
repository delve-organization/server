package com.github.delve.common.validator;

import com.github.delve.security.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailNotUsedValidator implements ConstraintValidator<EmailNotUsed, String> {

    private final UserService userService;

    @Autowired
    public EmailNotUsedValidator(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext constraintValidatorContext) {
        return !userService.existsByEmail(email);
    }
}
