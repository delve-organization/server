package com.github.delve.integrationtest.treecard.service;

import com.github.delve.common.domain.Accessibility;
import com.github.delve.common.exception.DelveException;
import com.github.delve.component.treecard.dto.CreateTreeCardCommand;
import com.github.delve.component.treecard.dto.DeleteTreeCardCommand;
import com.github.delve.component.treecard.dto.EditTreeCardCommand;
import com.github.delve.component.treecard.dto.TreeCardDto;
import com.github.delve.component.treecard.repository.TreeCardRepository;
import com.github.delve.component.treecard.service.TreeCardService;
import com.github.delve.dev.JwtAuthenticator;
import com.github.delve.integrationtest.SpringBootTestBase;
import com.github.delve.integrationtest.tree.util.TreeBaseData;
import com.github.delve.integrationtest.treecard.util.TreeCardBaseData;
import com.github.delve.integrationtest.user.util.UserBaseData;
import com.github.delve.integrationtest.util.auth.Authenticate;
import com.github.delve.integrationtest.util.basedata.Preload;
import com.github.delve.integrationtest.util.basedata.UseBaseData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static com.github.delve.common.domain.Accessibility.PRIVATE;
import static com.github.delve.common.domain.Accessibility.PUBLIC;
import static com.github.delve.dev.TreeCardTestData.TREE_CARD_0_ID;
import static com.github.delve.dev.TreeCardTestData.TREE_CARD_1_ID;
import static com.github.delve.dev.TreeCardTestData.TREE_CARD_2_ID;
import static com.github.delve.dev.TreeCardTestData.TREE_CARD_3_ID;
import static com.github.delve.dev.TreeTestData.TREE_0_ID;
import static com.github.delve.dev.TreeTestData.TREE_1_ID;
import static com.github.delve.integrationtest.treecard.util.TreeCardDtoMatcher.treeCardDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TreeCardServiceTest extends SpringBootTestBase {

    @Autowired
    private TreeCardService treeCardService;
    @Autowired
    private TreeCardRepository treeCardRepository;
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
        final Long savedTreeCardId = treeCardService.save(new CreateTreeCardCommand(TREE_0_ID, "Title", "description", "scifi_fantasy_books.png", "black", PRIVATE));
        final List<TreeCardDto> savedTreeCards = treeCardService.findAllAvailable();

        assertEquals(savedTreeCards.size(), 1);
        assertThat(savedTreeCards.get(0), treeCardDto()
                .hasId(savedTreeCardId)
                .hasTreeId(TREE_0_ID)
                .hasTitle("Title")
                .hasDescription("description")
                .hasImage("scifi_fantasy_books.png")
                .hasImageUrl("http://localhost/public/images/scifi_fantasy_books.png")
                .hasColor("black")
                .isEditable(true)
                .hasOwnerName("Test User"));

        treeCardRepository.deleteById(savedTreeCardId);
    }

    @Test(expected = DelveException.class)
    @Preload(UserBaseData.class)
    @Authenticate(username = "admin", password = "password")
    @UseBaseData(TreeBaseData.class)
    public void otherUserCannotCreateWithPrivateTree() {
        jwtAuthenticator.authenticate("user", "password");
        treeCardService.save(new CreateTreeCardCommand(TREE_1_ID, "Title", "description", "scifi_fantasy_books.png", "black", PRIVATE));
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeBaseData.class)
    public void adminCanEdit() {
        jwtAuthenticator.authenticate("user", "password");
        final Long savedTreeCardId = treeCardService.save(new CreateTreeCardCommand(TREE_0_ID, "Title", "description", "scifi_fantasy_books.png", "black", PRIVATE));

        jwtAuthenticator.authenticate("admin", "password");
        final List<TreeCardDto> savedTreeCards = treeCardService.findAllAvailable();
        assertEquals(savedTreeCards.size(), 1);
        assertThat(savedTreeCards.get(0), treeCardDto().isEditable(true));

        treeCardRepository.deleteById(savedTreeCardId);
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeBaseData.class)
    public void otherUserCannotEdit() {
        jwtAuthenticator.authenticate("admin", "password");
        final Long savedTreeCardId = treeCardService.save(new CreateTreeCardCommand(TREE_0_ID, "Title", "description", "scifi_fantasy_books.png", "black", PUBLIC));

        jwtAuthenticator.authenticate("user", "password");
        final List<TreeCardDto> savedTreeCards = treeCardService.findAllAvailable();
        assertEquals(savedTreeCards.size(), 1);
        assertThat(savedTreeCards.get(0), treeCardDto().isEditable(false));

        treeCardRepository.deleteById(savedTreeCardId);
    }

    @Test(expected = DelveException.class)
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    public void treeNotFound() {
        treeCardService.save(new CreateTreeCardCommand(TREE_0_ID, "Title", "description", "scifi_fantasy_books.png", "black", PRIVATE));
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "admin", password = "password")
    @UseBaseData(TreeCardBaseData.class)
    public void edit() {
        final TreeCardDto initialTreeCard = treeCardService.findById(TREE_CARD_0_ID);
        assertThat(initialTreeCard, treeCardDto()
                .hasId(TREE_CARD_0_ID)
                .hasTreeId(TREE_0_ID)
                .hasTitle("tree card title 1")
                .hasDescription("tree card description 1")
                .hasImage("scifi_fantasy_books.png")
                .hasImageUrl("http://localhost/public/images/scifi_fantasy_books.png")
                .hasColor("black")
                .hasAccessibility(PUBLIC)
                .isEditable(true)
                .hasOwnerName("Admin"));

        treeCardService.edit(new EditTreeCardCommand(TREE_CARD_0_ID, TREE_1_ID, "New title", "New description", "fat_cat.png", "yellow", Accessibility.PRIVATE));
        final TreeCardDto editedTreeCard = treeCardService.findById(TREE_CARD_0_ID);
        assertThat(editedTreeCard, treeCardDto()
                .hasId(TREE_CARD_0_ID)
                .hasTreeId(TREE_1_ID)
                .hasTitle("New title")
                .hasDescription("New description")
                .hasImage("fat_cat.png")
                .hasImageUrl("http://localhost/public/images/fat_cat.png")
                .hasColor("yellow")
                .hasAccessibility(PRIVATE)
                .isEditable(true)
                .hasOwnerName("Admin"));
    }

    @Test
    @Preload(UserBaseData.class)
    @Authenticate(username = "user", password = "password")
    @UseBaseData(TreeCardBaseData.class)
    public void delete() {
        final List<TreeCardDto> initialTreeCards = treeCardService.findAllAvailable();
        assertEquals(4, initialTreeCards.size());

        treeCardService.delete(new DeleteTreeCardCommand(TREE_CARD_0_ID));
        treeCardService.delete(new DeleteTreeCardCommand(TREE_CARD_1_ID));
        final List<TreeCardDto> modifiedTreeCards = treeCardService.findAllAvailable();
        assertEquals(2, modifiedTreeCards.size());
        assertThat(modifiedTreeCards.get(0), treeCardDto().hasId(TREE_CARD_2_ID));
        assertThat(modifiedTreeCards.get(1), treeCardDto().hasId(TREE_CARD_3_ID));
    }
}
