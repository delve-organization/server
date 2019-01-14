package com.github.delve.security.dto;

import com.github.delve.security.domain.RoleName;

import java.util.Set;

public class CreateUserCommand {

    public final String name;
    public final String username;
    public final String email;
    public final String password;
    public final Set<RoleName> roles;

    public CreateUserCommand(final String name, final String username, final String email, final String password, final Set<RoleName> roles) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
