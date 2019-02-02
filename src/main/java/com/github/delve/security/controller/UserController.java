package com.github.delve.security.controller;

import com.github.delve.config.RestApiController;
import com.github.delve.security.dto.EditUserCommand;
import com.github.delve.security.dto.EditUserRequest;
import com.github.delve.security.dto.UserDto;
import com.github.delve.security.service.user.UserPrinciple;
import com.github.delve.security.service.user.UserService;
import com.github.delve.security.util.UserUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RestApiController
@RequestMapping("user")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/edit")
    public UserDto editUser(@Valid @RequestBody final EditUserRequest request) {
        final UserPrinciple userPrinciple = UserUtil.currentUser();

        return userService.edit(new EditUserCommand(
                userPrinciple.getId(),
                request.getName()
        ));
    }

    @GetMapping("/get-user")
    public UserDto getUser(@RequestParam final Long userId) {
        return userService.getUser(userId);
    }
}
