package com.github.delve.security.util;

import com.github.delve.common.exception.DelveException;
import com.github.delve.security.service.user.UserPrinciple;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    private UserUtil() {
    }

    public static UserPrinciple currentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new DelveException("User not authenticated.");
        }
        return (UserPrinciple) authentication.getPrincipal();
    }

    public static boolean isAdmin(final UserPrinciple user) {
        return user.getAuthorities().contains(UserPrinciple.ADMIN_AUTHORITY);
    }
}
