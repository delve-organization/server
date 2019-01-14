package com.github.delve.security.repository;

import com.github.delve.security.domain.Role;
import com.github.delve.security.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);
}
