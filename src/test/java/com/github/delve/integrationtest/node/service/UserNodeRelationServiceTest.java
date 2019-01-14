package com.github.delve.integrationtest.node.service;

import com.github.delve.component.node.dto.CreateNodeCommand;
import com.github.delve.component.node.dto.CreateUserNodeRelationCommand;
import com.github.delve.component.node.dto.UserNodeRelationDto;
import com.github.delve.component.node.service.NodeService;
import com.github.delve.component.node.service.UserNodeRelationService;
import com.github.delve.integrationtest.node.util.NodeBaseData;
import com.github.delve.integrationtest.node.util.UserNodeRelationDtoMatcher;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.basedata.UseBaseData;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.util.auth.Authenticate;
import com.github.delve.integrationtest.util.basedata.Preload;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.delve.dev.NodeTestData.LEVEL_2_0_ID;
import static com.github.delve.dev.NodeTestData.ROOT_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UserNodeRelationServiceTest extends SpringBootTestBase {

    @Autowired
    private UserNodeRelationService userNodeRelationService;
    @Autowired
    private NodeService nodeService;

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(NodeBaseData.class)
    public void setNodeVisitedForLeaf() {
        final List<UserNodeRelationDto> beforeRelations = userNodeRelationService.getRelationsByUser();
        assertEquals(0, beforeRelations.size());

        saveRelation(LEVEL_2_0_ID, true);

        final List<UserNodeRelationDto> afterRelations = userNodeRelationService.getRelationsByUser();
        assertEquals(1, afterRelations.size());
        assertThat(afterRelations.get(0), UserNodeRelationDtoMatcher.userNodeRelationDtoMatcher().hasNodeId(LEVEL_2_0_ID).hasVisited(true));
    }

    @Test(expected = IllegalStateException.class)
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(NodeBaseData.class)
    public void setNodeVisitedRootNode() {
        saveRelation(ROOT_ID, true);
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    public void newChildNodeRemovesParentRelationRecords() {
        final Long rootNodeId = nodeService.save(new CreateNodeCommand("root", "description"));
        saveRelation(rootNodeId, true);

        final Long childNodeId = nodeService.save(new CreateNodeCommand("child1", "description", rootNodeId));
        saveRelation(childNodeId, true);

        final List<UserNodeRelationDto> savedRelations = userNodeRelationService.getRelationsByUser();

        assertEquals(savedRelations.size(), 1);
        assertThat(savedRelations.get(0), UserNodeRelationDtoMatcher.userNodeRelationDtoMatcher().hasNodeId(childNodeId).hasVisited(true));
    }

    private void saveRelation(final Long nodeId, final Boolean visited) {
        final CreateUserNodeRelationCommand command = new CreateUserNodeRelationCommand();
        command.setNodeId(nodeId);
        command.setVisited(visited);
        userNodeRelationService.save(command);
    }
}
