package com.github.delve.dev;

import com.github.delve.component.treecard.dto.CreateTreeCardCommand;
import com.github.delve.component.treecard.service.TreeCardService;

import static com.github.delve.common.domain.Accessibility.PRIVATE;
import static com.github.delve.common.domain.Accessibility.PUBLIC;
import static com.github.delve.dev.TreeTestData.TREE_0_ID;
import static com.github.delve.dev.TreeTestData.TREE_1_ID;

public class TreeCardTestData {

    public static Long TREE_CARD_0_ID;
    public static Long TREE_CARD_1_ID;
    public static Long TREE_CARD_2_ID;
    public static Long TREE_CARD_3_ID;

    public static void createTestData(final TreeCardService treeCardService) {
        TREE_CARD_0_ID = treeCardService.save(new CreateTreeCardCommand(TREE_0_ID, "tree card title 1", "tree card description 1", "scifi_fantasy_books.png", "black", PUBLIC));
        TREE_CARD_1_ID = treeCardService.save(new CreateTreeCardCommand(TREE_0_ID, "tree card title 2", "tree card description 2", "scifi_fantasy_books.png", "brown", PUBLIC));
        TREE_CARD_2_ID = treeCardService.save(new CreateTreeCardCommand(TREE_1_ID, "tree card title 3", "tree card description 3", "scifi_fantasy_books.png", "yellow", PRIVATE));
        TREE_CARD_3_ID = treeCardService.save(new CreateTreeCardCommand(TREE_1_ID, "tree card title 4", "tree card description 4", "scifi_fantasy_books.png", "red", PRIVATE));
    }
}
