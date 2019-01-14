package com.github.delve.security.dto;

import com.github.delve.security.domain.RoleName;

public class CreateRoleCommand {

    public final RoleName name;

    public CreateRoleCommand(final RoleName name) {
        this.name = name;
    }
}
