package com.github.delve.common.validator;

import com.github.delve.security.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameNotUsedValidator implements ConstraintValidator<UsernameNotUsed, String> {

    private final UserService userService;

    @Autowired
    public UsernameNotUsedValidator(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext constraintValidatorContext) {
        return !userService.existsByUsername(username);
    }
}
