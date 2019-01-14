package com.github.delve.dev;

import com.github.delve.component.tree.dto.CreateTreeCommand;
import com.github.delve.component.tree.service.TreeService;

import static com.github.delve.common.domain.Accessibility.PRIVATE;
import static com.github.delve.common.domain.Accessibility.PUBLIC;
import static com.github.delve.dev.NodeTestData.LEVEL_3_0_ID;
import static com.github.delve.dev.NodeTestData.ROOT_ID;

public class TreeTestData {

    public static Long TREE_0_ID;
    public static Long TREE_1_ID;

    public static void createTestData(final TreeService treeService) {
        TREE_0_ID = treeService.save(new CreateTreeCommand(ROOT_ID, "Tree title 1", PUBLIC));
        TREE_1_ID = treeService.save(new CreateTreeCommand(LEVEL_3_0_ID, "Tree title 2", PRIVATE));
    }
}
