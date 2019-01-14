package com.github.delve.security.service.role;

import com.github.delve.security.repository.RoleRepository;
import com.github.delve.security.domain.Role;
import com.github.delve.security.domain.RoleName;
import com.github.delve.security.dto.CreateRoleCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByName(final RoleName roleName) {
        return roleRepository.findByName(roleName);
    }

    public Long save(final CreateRoleCommand command) {
        final Role role = new Role(command.name);

        return roleRepository.save(role).getId();
    }
}
