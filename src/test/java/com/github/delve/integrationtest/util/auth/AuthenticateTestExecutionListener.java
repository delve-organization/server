package com.github.delve.integrationtest.util.auth;

import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.integrationtest.util.AnnotationUtil;
import com.github.delve.integrationtest.util.context.ContextProvider;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class AuthenticateTestExecutionListener implements TestExecutionListener {

    @Override
    public void beforeTestMethod(final TestContext testContext) {
        final Authenticate authenticateAnnotation = AnnotationUtil.findAnnotationOnMethod(testContext.getTestMethod(), Authenticate.class);
        if (authenticateAnnotation == null) {
            return;
        }
        final JwtAuthenticator jwtAuthenticator = ContextProvider.getBean(JwtAuthenticator.class);

        jwtAuthenticator.authenticate(authenticateAnnotation.username(), authenticateAnnotation.password());
    }

    @Override
    public void afterTestMethod(final TestContext testContext) {
        final Authenticate authenticateAnnotation = AnnotationUtil.findAnnotationOnMethod(testContext.getTestMethod(), Authenticate.class);
        if (authenticateAnnotation == null) {
            return;
        }
        final JwtAuthenticator jwtAuthenticator = ContextProvider.getBean(JwtAuthenticator.class);

        jwtAuthenticator.deAuthenticate();
    }
}
