package com.github.delve.component.admin.controller;

import com.github.delve.component.admin.dto.UpdateUserRequest;
import com.github.delve.config.RestApiController;
import com.github.delve.security.dto.UserDto;
import com.github.delve.security.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@RestApiController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-users")
    public Page<UserDto> getUsers(@RequestParam("orderBy") String orderBy,
                                  @RequestParam("direction") String direction,
                                  @RequestParam("page") int page,
                                  @RequestParam("size") int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), orderBy));
        return userService.getUsers(pageable);
    }

    @PostMapping("/update-users")
    public void updateUsers(@Valid @RequestBody final List<UpdateUserRequest> updateUserRequests) {
        userService.updateUsers(updateUserRequests);
    }
}
