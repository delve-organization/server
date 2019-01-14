package com.github.delve.dev;

import com.github.delve.component.treeboard.dto.CreateTreeBoardCommand;
import com.github.delve.component.treeboard.service.TreeBoardService;

import static com.github.delve.common.domain.Accessibility.PRIVATE;
import static com.github.delve.common.domain.Accessibility.PUBLIC;
import static com.github.delve.dev.TreeTestData.TREE_0_ID;
import static com.github.delve.dev.TreeTestData.TREE_1_ID;

public class TreeBoardTestData {

    public static Long TREE_BOARD_0_ID;
    public static Long TREE_BOARD_1_ID;
    public static Long TREE_BOARD_2_ID;
    public static Long TREE_BOARD_3_ID;

    public static void createTestData(final TreeBoardService treeBoardService) {
        TREE_BOARD_0_ID = treeBoardService.save(new CreateTreeBoardCommand(TREE_0_ID, "Tree board title 1", "Tree board description 1", "scifi_fantasy_books.png", "black", PUBLIC));
        TREE_BOARD_1_ID = treeBoardService.save(new CreateTreeBoardCommand(TREE_0_ID, "Tree board title 2", "Tree board description 2", "scifi_fantasy_books.png", "brown", PUBLIC));
        TREE_BOARD_2_ID = treeBoardService.save(new CreateTreeBoardCommand(TREE_1_ID, "Tree board title 3", "Tree board description 3", "scifi_fantasy_books.png", "yellow", PRIVATE));
        TREE_BOARD_3_ID = treeBoardService.save(new CreateTreeBoardCommand(TREE_1_ID, "Tree board title 4", "Tree board description 4", "scifi_fantasy_books.png", "red", PRIVATE));
    }
}
