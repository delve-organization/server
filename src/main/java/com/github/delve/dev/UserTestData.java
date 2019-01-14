package com.github.delve.dev;

import com.github.delve.security.dto.CreateUserCommand;
import com.github.delve.security.service.user.UserService;

import java.util.HashSet;

import static com.github.delve.security.domain.RoleName.ROLE_ADMIN;
import static com.github.delve.security.domain.RoleName.ROLE_USER;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class UserTestData {

    public static Long ADMIN_ID;

    public static void createTestData(final UserService userService) {
        ADMIN_ID = userService.save(new CreateUserCommand("Admin", "admin", "admin@delve.com", "password", new HashSet<>(asList(ROLE_USER, ROLE_ADMIN))));
        userService.save(new CreateUserCommand("Test User", "user", "user@delve.com", "password", new HashSet<>(singletonList(ROLE_USER))));
        userService.save(new CreateUserCommand("Corrina Sandal", "csandal0", "csandal0@utexas.edu", "IWpumnmnf3N7", new HashSet<>(singletonList(ROLE_USER))));
        userService.save(new CreateUserCommand("Maureen Elijahu", "melijahu1", "melijahu1@cdbaby.com", "KxVkUgWyGTI1", new HashSet<>(singletonList(ROLE_USER))));
        userService.save(new CreateUserCommand("Granthem Cuthbertson", "gcuthbertson2", "gcuthbertson2@networksolutions.com", "Gs0td68g", new HashSet<>(singletonList(ROLE_USER))));
        userService.save(new CreateUserCommand("Tobi Cotillard", "tcotillard3", "tcotillard3@meetup.com", "aO78UifF", new HashSet<>(singletonList(ROLE_USER))));
        userService.save(new CreateUserCommand("Giordano Pactat", "gpactat4", "gpactat4@hubpages.com", "MuqAojy2hbO", new HashSet<>(singletonList(ROLE_USER))));
        userService.save(new CreateUserCommand("Klaus Mullany", "kmullany5", "kmullany5@usatoday.com", "yPi56Hb", new HashSet<>(singletonList(ROLE_USER))));
        userService.save(new CreateUserCommand("Shana Simondson", "ssimondson6", "ssimondson6@buzzfeed.com", "tv9FWkS", new HashSet<>(singletonList(ROLE_USER))));
        userService.save(new CreateUserCommand("Clemmie Winchcum", "cwinchcum7", "cwinchcum7@fda.gov", "supw7apRrJ4", new HashSet<>(singletonList(ROLE_USER))));
        userService.save(new CreateUserCommand("Devin Flucker", "dflucker8", "dflucker8@wordpress.com", "t0N8HKVd", new HashSet<>(singletonList(ROLE_USER))));
        userService.save(new CreateUserCommand("Dionis Downer", "ddowner9", "ddowner9@pcworld.com", "wBqHxatj", new HashSet<>(singletonList(ROLE_USER))));
    }
}
