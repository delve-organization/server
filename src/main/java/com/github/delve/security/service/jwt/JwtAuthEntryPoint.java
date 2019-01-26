package com.github.delve.security.service.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException e)
            throws IOException {
        logger.error("Unauthorized error. Message - {}", e.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
    }
}
