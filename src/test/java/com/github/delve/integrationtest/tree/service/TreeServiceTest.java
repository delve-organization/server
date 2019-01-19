package com.github.delve.integrationtest.tree.service;

import com.github.delve.component.tree.dto.CreateTreeCommand;
import com.github.delve.component.tree.dto.TreeDto;
import com.github.delve.component.tree.repository.TreeRepository;
import com.github.delve.component.tree.service.TreeService;
import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.integrationtest.tree.util.TreeBaseData;
import com.github.delve.integrationtest.tree.util.TreeDtoMatcher;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.basedata.UseBaseData;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.node.util.NodeBaseData;
import com.github.delve.integrationtest.util.auth.Authenticate;
import com.github.delve.integrationtest.util.basedata.Preload;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.delve.common.domain.Accessibility.PRIVATE;
import static com.github.delve.common.domain.Accessibility.PUBLIC;
import static com.github.delve.dev.NodeTestData.ROOT_ID;
import static com.github.delve.dev.TreeTestData.TREE_1_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TreeServiceTest extends SpringBootTestBase {

    @Autowired
    private TreeService treeService;
    @Autowired
    private TreeRepository treeRepository;
    @Autowired
    private JwtAuthenticator jwtAuthenticator;

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(NodeBaseData.class)
    public void saveTree() {
        final Long savedTreeId = treeService.save(new CreateTreeCommand(ROOT_ID, "Scifi or Fantasy", PRIVATE));
        final List<TreeDto> savedTrees = treeService.findAllAvailable();

        assertEquals(savedTrees.size(), 1);
        assertThat(savedTrees.get(0), TreeDtoMatcher.treeDto()
                .hasId(savedTreeId)
                .hasRootNodeId(ROOT_ID)
                .hasTitle("Scifi or Fantasy")
                .isEditable(true)
                .hasAccessibility(PRIVATE));

        treeRepository.deleteById(savedTreeId);
    }

    @Test
    @UseBaseData({UserBaseData.class, NodeBaseData.class})
    public void adminCanEdit() {
        jwtAuthenticator.authenticate("user", "password");
        final Long savedTreeId = treeService.save(new CreateTreeCommand(ROOT_ID, "Scifi or Fantasy", PRIVATE));

        jwtAuthenticator.authenticate("admin", "password");
        final List<TreeDto> savedTrees = treeService.findAllAvailable();
        assertEquals(savedTrees.size(), 1);
        assertThat(savedTrees.get(0), TreeDtoMatcher.treeDto().isEditable(true));

        treeRepository.deleteById(savedTreeId);
    }

    @Test
    @UseBaseData({UserBaseData.class, NodeBaseData.class})
    public void otherUserCannotEdit() {
        jwtAuthenticator.authenticate("admin", "password");
        final Long savedTreeId = treeService.save(new CreateTreeCommand(ROOT_ID, "Scifi or Fantasy", PUBLIC));

        jwtAuthenticator.authenticate("user", "password");
        final List<TreeDto> savedTrees = treeService.findAllAvailable();
        assertEquals(savedTrees.size(), 1);
        assertThat(savedTrees.get(0), TreeDtoMatcher.treeDto().isEditable(false));

        treeRepository.deleteById(savedTreeId);
    }

    @Test(expected = IllegalStateException.class)
    @Preload(UserBaseData.class)
    @Authenticate(username = "admin", password = "password")
    @UseBaseData(TreeBaseData.class)
    public void otherUserCannotGetPrivateTree() {
        jwtAuthenticator.authenticate("user", "password");
        treeService.findById(TREE_1_ID);
    }

    @Test(expected = IllegalStateException.class)
    public void rootNodeNotFound() {
        treeService.save(new CreateTreeCommand(-1L, "title", PRIVATE));
    }
}
