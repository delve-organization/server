package com.github.delve.security.controller;

import com.github.delve.config.RestApiController;
import com.github.delve.security.dto.CreateUserCommand;
import com.github.delve.security.dto.JwtResponse;
import com.github.delve.security.dto.LoginRequest;
import com.github.delve.security.dto.SignUpRequest;
import com.github.delve.security.service.jwt.JwtProvider;
import com.github.delve.security.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashSet;

import static com.github.delve.security.domain.RoleName.ROLE_USER;
import static java.util.Collections.singletonList;

@RestApiController
@RequestMapping("auth")
public class AuthenticationController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthenticationController(final AuthenticationManager authenticationManager, final UserService userService, final JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/signin")
    public JwtResponse authenticateUser(@Valid @RequestBody final LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.info("User {} authenticated", loginRequest.getUsername());

        final String jwt = jwtProvider.generateJwtToken(authentication);
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
    }

    @PostMapping("/signup")
    public void registerUser(@Valid @RequestBody final SignUpRequest signUpRequest) {
        userService.save(new CreateUserCommand(
                signUpRequest.getName(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                new HashSet<>(singletonList(ROLE_USER))));
    }
}
