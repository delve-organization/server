package com.github.delve.security.service.user;

import com.github.delve.component.admin.dto.UpdateUserRequest;
import com.github.delve.security.domain.Role;
import com.github.delve.security.domain.RoleName;
import com.github.delve.security.domain.User;
import com.github.delve.security.dto.CreateUserCommand;
import com.github.delve.security.dto.UserDto;
import com.github.delve.security.repository.UserRepository;
import com.github.delve.security.service.role.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(final UserRepository userRepository, final RoleService roleService, final PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    public String getUserNameById(final Long userId) {
        return userRepository.getUserNameById(userId);
    }

    public Boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    public Long save(final CreateUserCommand command) {
        final User user = new User(
                command.name,
                command.username,
                command.email,
                encoder.encode(command.password),
                createRolesFromRoleNames(command.roles)
        );

        final User savedUser = userRepository.save(user);

        logger.info("Saved new user with id: {}", savedUser.getId());
        return savedUser.getId();
    }

    public Page<UserDto> getUsers(final Pageable pageable) {
        return userRepository.findAll(pageable).map(user -> new UserDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
        );
    }

    public void updateUsers(final List<UpdateUserRequest> requests) {
        final List<User> updatedUsers = requests.stream()
                .map(request -> new UserAndRequest(userRepository.findById(request.getId()), request))
                .filter(userAndRequest -> userAndRequest.user.isPresent())
                .map(userAndRequest -> {
                    final User user = userAndRequest.user.get();
                    user.setName(userAndRequest.request.getName());
                    user.setUsername(userAndRequest.request.getUsername());
                    user.setEmail(userAndRequest.request.getEmail());
                    user.setRoles(createRolesFromRoleNames(userAndRequest.request.getRoles()));

                    return user;
                }).collect(Collectors.toList());

        userRepository.saveAll(updatedUsers);
        for (User user : updatedUsers) {
            logger.info("Updated user with id: {}", user.getId());
        }
    }

    private Set<Role> createRolesFromRoleNames(final Set<RoleName> names) {
        return names.stream()
                .map(roleService::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private static class UserAndRequest {
        private final Optional<User> user;
        private final UpdateUserRequest request;

        private UserAndRequest(final Optional<User> user, final UpdateUserRequest request) {
            this.user = user;
            this.request = request;
        }
    }
}
