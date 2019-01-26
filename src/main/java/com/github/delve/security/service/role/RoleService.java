package com.github.delve.security.service.role;

import com.github.delve.security.repository.RoleRepository;
import com.github.delve.security.domain.Role;
import com.github.delve.security.domain.RoleName;
import com.github.delve.security.dto.CreateRoleCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
        final Role savedRole = roleRepository.save(role);

        logger.info("Saved new role with id: {}", savedRole.getId());
        return savedRole.getId();
    }
}
