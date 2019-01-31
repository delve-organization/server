package com.github.delve.integrationtest.util.validator;

import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.basedata.UseBaseData;
import com.github.delve.security.dto.SignUpRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SignUpRequestValidationTest extends SpringBootTestBase {

    @Autowired
    private Validator validator;

    @Test
    public void validRequest() {
        final SignUpRequest request = new SignUpRequest();
        request.setName("Admin");
        request.setUsername("admin");
        request.setEmail("admin@delve.com");
        request.setPassword("password");

        final Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @UseBaseData(UserBaseData.class)
    public void usernameUsed() {
        final SignUpRequest request = new SignUpRequest();
        request.setName("Admin");
        request.setUsername("admin");
        request.setEmail("admin@gmail.com");
        request.setPassword("password");

        final Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());

        final ConstraintViolation<SignUpRequest> violation = violations.iterator().next();
        assertEquals("validation.constraints.usernamenotused", violation.getMessage());
    }

    @Test
    @UseBaseData(UserBaseData.class)
    public void emailUsed() {
        final SignUpRequest request = new SignUpRequest();
        request.setName("Admin");
        request.setUsername("admins");
        request.setEmail("admin@delve.com");
        request.setPassword("password");

        final Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());

        final ConstraintViolation<SignUpRequest> violation = violations.iterator().next();
        assertEquals("validation.constraints.emailnotused", violation.getMessage());
    }
}
