package com.github.delve.security.controller;

import com.github.delve.config.RestApiController;
import com.github.delve.security.dto.CreateUserCommand;
import com.github.delve.security.dto.JwtResponse;
import com.github.delve.security.dto.LoginRequest;
import com.github.delve.security.dto.SignUpRequest;
import com.github.delve.security.service.jwt.JwtProvider;
import com.github.delve.security.service.user.UserPrinciple;
import com.github.delve.security.service.user.UserService;
import com.github.delve.security.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public JwtResponse authenticateUser(@Valid @RequestBody final LoginRequest request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.info("User {} authenticated", request.getUsername());

        final String jwt = jwtProvider.generateJwtToken(authentication);
        final UserPrinciple userPrinciple = UserUtil.currentUser();

        return new JwtResponse(jwt, userPrinciple.getId(), userPrinciple.getUsername(), userPrinciple.getAuthorities());
    }

    @PostMapping("/signup")
    public void registerUser(@Valid @RequestBody final SignUpRequest request) {
        userService.save(new CreateUserCommand(
                request.getName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                new HashSet<>(singletonList(ROLE_USER))));
    }
}
