package com.github.delve.integrationtest.treeboard.service;

import com.github.delve.component.treeboard.dto.CreateTreeBoardCommand;
import com.github.delve.component.treeboard.dto.DeleteTreeBoardCommand;
import com.github.delve.component.treeboard.dto.TreeBoardDto;
import com.github.delve.component.treeboard.repository.TreeBoardRepository;
import com.github.delve.component.treeboard.service.TreeBoardService;
import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.integrationtest.tree.util.TreeBaseData;
import com.github.delve.integrationtest.treeboard.util.TreeBoardBaseData;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.basedata.UseBaseData;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.util.auth.Authenticate;
import com.github.delve.integrationtest.util.basedata.Preload;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static com.github.delve.common.domain.Accessibility.PRIVATE;
import static com.github.delve.common.domain.Accessibility.PUBLIC;
import static com.github.delve.dev.TreeBoardTestData.TREE_BOARD_0_ID;
import static com.github.delve.dev.TreeBoardTestData.TREE_BOARD_1_ID;
import static com.github.delve.dev.TreeBoardTestData.TREE_BOARD_2_ID;
import static com.github.delve.dev.TreeBoardTestData.TREE_BOARD_3_ID;
import static com.github.delve.dev.TreeTestData.TREE_0_ID;
import static com.github.delve.dev.TreeTestData.TREE_1_ID;
import static com.github.delve.integrationtest.treeboard.util.TreeBoardDtoMatcher.treeBoardDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TreeBoardServiceTest extends SpringBootTestBase {

    @Autowired
    private TreeBoardService treeBoardService;
    @Autowired
    private TreeBoardRepository treeBoardRepository;
    @Autowired
    private JwtAuthenticator jwtAuthenticator;

    @Before
    public void setUp() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeBaseData.class)
    public void saveAnFindAllAvailable() {
        final Long savedTreeBoardId = treeBoardService.save(new CreateTreeBoardCommand(TREE_0_ID, "Title", "description", "scifi_fantasy_books.png", "black", PRIVATE));
        final List<TreeBoardDto> savedTreeBoards = treeBoardService.findAllAvailable();

        assertEquals(savedTreeBoards.size(), 1);
        assertThat(savedTreeBoards.get(0), treeBoardDto()
                .hasId(savedTreeBoardId)
                .hasTreeId(TREE_0_ID)
                .hasTitle("Title")
                .hasDescription("description")
                .hasImageUrl("http://localhost/images/scifi_fantasy_books.png")
                .hasColor("black")
                .isEditable(true));

        treeBoardRepository.deleteById(savedTreeBoardId);
    }

    @Test(expected = IllegalStateException.class)
    @Preload(UserBaseData.class)
    @Authenticate(username = "admin", password = "password")
    @UseBaseData(TreeBaseData.class)
    public void otherUserCannotCreateWithPrivateTree() {
        jwtAuthenticator.authenticate("user", "password");
        treeBoardService.save(new CreateTreeBoardCommand(TREE_1_ID, "Title", "description", "scifi_fantasy_books.png", "black", PRIVATE));
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeBaseData.class)
    public void adminCanEdit() {
        jwtAuthenticator.authenticate("user", "password");
        final Long savedTreeBoardId = treeBoardService.save(new CreateTreeBoardCommand(TREE_0_ID, "Title", "description", "scifi_fantasy_books.png", "black", PRIVATE));

        jwtAuthenticator.authenticate("admin", "password");
        final List<TreeBoardDto> savedTreeBoards = treeBoardService.findAllAvailable();
        assertEquals(savedTreeBoards.size(), 1);
        assertThat(savedTreeBoards.get(0), treeBoardDto().isEditable(true));

        treeBoardRepository.deleteById(savedTreeBoardId);
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeBaseData.class)
    public void otherUserCannotEdit() {
        jwtAuthenticator.authenticate("admin", "password");
        final Long savedTreeBoardId = treeBoardService.save(new CreateTreeBoardCommand(TREE_0_ID, "Title", "description", "scifi_fantasy_books.png", "black", PUBLIC));

        jwtAuthenticator.authenticate("user", "password");
        final List<TreeBoardDto> savedTreeBoards = treeBoardService.findAllAvailable();
        assertEquals(savedTreeBoards.size(), 1);
        assertThat(savedTreeBoards.get(0), treeBoardDto().isEditable(false));

        treeBoardRepository.deleteById(savedTreeBoardId);
    }

    @Test(expected = IllegalStateException.class)
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    public void treeNotFound() {
        treeBoardService.save(new CreateTreeBoardCommand(TREE_0_ID, "Title", "description", "scifi_fantasy_books.png", "black", PRIVATE));
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeBoardBaseData.class)
    public void delete() {
        final List<TreeBoardDto> initialTreeBoards = treeBoardService.findAllAvailable();
        assertEquals(4, initialTreeBoards.size());

        treeBoardService.delete(new DeleteTreeBoardCommand(TREE_BOARD_0_ID));
        treeBoardService.delete(new DeleteTreeBoardCommand(TREE_BOARD_1_ID));
        final List<TreeBoardDto> modifiedTreeBoards = treeBoardService.findAllAvailable();
        assertEquals(2, modifiedTreeBoards.size());
        assertThat(modifiedTreeBoards.get(0), treeBoardDto().hasId(TREE_BOARD_2_ID));
        assertThat(modifiedTreeBoards.get(1), treeBoardDto().hasId(TREE_BOARD_3_ID));
    }
}
