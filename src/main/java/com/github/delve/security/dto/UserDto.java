package com.github.delve.security.dto;

import com.github.delve.security.domain.RoleName;

import java.util.Set;

public class UserDto {

    public final Long id;
    public final String name;
    public final String username;
    public final String email;
    public final Set<RoleName> roles;

    public UserDto(final Long id, final String name, final String username, final String email, final Set<RoleName> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
