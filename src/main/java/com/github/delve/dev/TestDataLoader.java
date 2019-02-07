package com.github.delve.dev;

import com.github.delve.component.node.service.NodeService;
import com.github.delve.component.tree.service.TreeService;
import com.github.delve.component.treecard.service.TreeCardService;
import com.github.delve.security.service.role.RoleService;
import com.github.delve.security.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class TestDataLoader {

    private final NodeService nodeService;
    private final TreeService treeService;
    private final RoleService roleService;
    private final UserService userService;
    private final TreeCardService treeCardService;
    private final JwtAuthenticator jwtAuthenticator;

    @Autowired
    public TestDataLoader(final NodeService nodeService, final TreeService treeService, final RoleService roleService,
                          final UserService userService, final TreeCardService treeCardService, final JwtAuthenticator jwtAuthenticator) {
        this.nodeService = nodeService;
        this.treeService = treeService;
        this.roleService = roleService;
        this.userService = userService;
        this.treeCardService = treeCardService;
        this.jwtAuthenticator = jwtAuthenticator;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createTestData() {
        RoleTestData.createTestData(roleService);
        UserTestData.createTestData(userService);
        jwtAuthenticator.authenticate("admin", "password");

        NodeTestData.createTestData(nodeService);
        TreeTestData.createTestData(treeService);
        TreeCardTestData.createTestData(treeCardService);

        jwtAuthenticator.deAuthenticate();
    }
}
