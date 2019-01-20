package com.github.delve.integrationtest.user.service;

import com.github.delve.component.admin.dto.UpdateUserRequest;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.basedata.UseBaseData;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.security.domain.Role;
import com.github.delve.security.domain.User;
import com.github.delve.security.dto.UserDto;
import com.github.delve.security.repository.UserRepository;
import com.github.delve.security.service.user.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.delve.dev.UserTestData.ADMIN_ID;
import static com.github.delve.integrationtest.user.util.UserDtoMatcher.userDto;
import static com.github.delve.security.domain.RoleName.ROLE_ADMIN;
import static com.github.delve.security.domain.RoleName.ROLE_USER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserServiceTest extends SpringBootTestBase {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @UseBaseData(UserBaseData.class)
    public void getPagedUsersTest() {
        final Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "id"));
        final Page<UserDto> usersPage = userService.getUsers(pageable);

        assertEquals(usersPage.getTotalElements(), 12);
        assertEquals(usersPage.getNumberOfElements(), 2);

        final List<UserDto> users = usersPage.getContent();

        assertThat(users.get(0), userDto()
                .hasName("Admin")
                .hasUsername("admin")
                .hasEmail("admin@delve.com")
                .hasRoles(ROLE_ADMIN, ROLE_USER)
        );
        assertThat(users.get(1), userDto()
                .hasName("Test User")
                .hasUsername("user")
                .hasEmail("user@delve.com")
                .hasRoles(ROLE_USER)
        );
    }

    @Test
    @UseBaseData(UserBaseData.class)
    public void updateUsersTest() {
        final User adminUser = userRepository.findById(ADMIN_ID).get();
        final UpdateUserRequest request = new UpdateUserRequest();
        request.setId(adminUser.getId());
        request.setName(adminUser.getName());
        request.setUsername(adminUser.getUsername());
        request.setEmail(adminUser.getEmail());
        request.setRoles(Collections.singleton(ROLE_USER));

        final UserDto adminDto = userToDto(adminUser);
        assertThat(adminDto, userDto()
                .hasName("Admin")
                .hasUsername("admin")
                .hasEmail("admin@delve.com")
                .hasRoles(ROLE_ADMIN, ROLE_USER)
        );

        userService.updateUsers(Collections.singletonList(request));

        final User newAdminUser = userRepository.findById(ADMIN_ID).get();
        final UserDto newAdminDto = userToDto(newAdminUser);
        assertThat(newAdminDto, userDto()
                .hasName("Admin")
                .hasUsername("admin")
                .hasEmail("admin@delve.com")
                .hasRoles(ROLE_USER)
        );
    }

    @Test
    @UseBaseData(UserBaseData.class)
    public void existsByUsername() {
        final boolean adminExists = userService.existsByUsername("admin");
        assertTrue(adminExists);
    }

    @Test
    public void notExistsByUsername() {
        final boolean adminExists = userService.existsByUsername("admin");
        assertFalse(adminExists);
    }

    @Test
    @UseBaseData(UserBaseData.class)
    public void existsByEmail() {
        final boolean adminExists = userService.existsByEmail("admin@delve.com");
        assertTrue(adminExists);
    }

    @Test
    public void notExistsByEmail() {
        final boolean adminExists = userService.existsByEmail("admin@delve.com");
        assertFalse(adminExists);
    }

    @Test
    @UseBaseData(UserBaseData.class)
    public void getUserNameById() {
        final String adminName = userService.getUserNameById(ADMIN_ID);
        assertEquals(adminName, "Admin");
    }

    private UserDto userToDto(final User user) {
        return new UserDto(user.getId(), user.getName(), user.getUsername(), user.getEmail(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
    }
}
