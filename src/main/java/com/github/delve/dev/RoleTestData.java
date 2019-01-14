package com.github.delve.dev;

import com.github.delve.security.dto.CreateRoleCommand;
import com.github.delve.security.service.role.RoleService;

import static com.github.delve.security.domain.RoleName.ROLE_ADMIN;
import static com.github.delve.security.domain.RoleName.ROLE_USER;

public class RoleTestData {

    public static void createTestData(final RoleService roleService) {
        roleService.save(new CreateRoleCommand(ROLE_ADMIN));
        roleService.save(new CreateRoleCommand(ROLE_USER));
    }
}
