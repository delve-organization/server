package com.github.delve.dev;

import com.github.delve.security.service.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticator {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public JwtAuthenticator(final AuthenticationManager authenticationManager, final JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public String authenticate(final String username, final String password) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return generateToken();
    }

    public void deAuthenticate() {
        SecurityContextHolder.clearContext();
    }

    public String generateToken() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return jwtProvider.generateJwtToken(authentication);
    }
}
