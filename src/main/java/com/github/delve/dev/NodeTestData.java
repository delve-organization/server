package com.github.delve.dev;

import com.github.delve.component.node.dto.CreateNodeCommand;
import com.github.delve.component.node.service.NodeService;

public class NodeTestData {

    public static Long ROOT_ID;
    public static Long LEVEL_1_0_ID;
    public static Long LEVEL_1_1_ID;

    public static Long LEVEL_2_0_ID;
    public static Long LEVEL_2_1_ID;
    public static Long LEVEL_2_2_ID;

    public static Long LEVEL_3_0_ID;
    public static Long LEVEL_3_1_ID;
    public static Long LEVEL_3_2_ID;

    public static Long LEVEL_4_0_ID;
    public static Long LEVEL_4_1_ID;
    public static Long LEVEL_4_2_ID;
    public static Long LEVEL_4_3_ID;

    public static void createTestData(final NodeService nodeService) {
        ROOT_ID = nodeService.save(new CreateNodeCommand("ROOT", "Description of ROOT", "fat_cat.png"));

        LEVEL_1_0_ID = nodeService.save(new CreateNodeCommand("LEVEL_1_0", "Description of LEVEL_1_0", ROOT_ID));
        LEVEL_1_1_ID = nodeService.save(new CreateNodeCommand("LEVEL_1_1", "Description of LEVEL_1_1", ROOT_ID));

        LEVEL_2_0_ID = nodeService.save(new CreateNodeCommand("LEVEL_2_0", "Description of LEVEL_2_0", LEVEL_1_0_ID));
        LEVEL_2_1_ID = nodeService.save(new CreateNodeCommand("LEVEL_2_1", "Description of LEVEL_2_1", LEVEL_1_0_ID));
        LEVEL_2_2_ID = nodeService.save(new CreateNodeCommand("LEVEL_2_2", "Description of LEVEL_2_2", LEVEL_1_1_ID));

        LEVEL_3_0_ID = nodeService.save(new CreateNodeCommand("LEVEL_3_0", "Description of LEVEL_3_0", LEVEL_2_2_ID));
        LEVEL_3_1_ID = nodeService.save(new CreateNodeCommand("LEVEL_3_1", "Description of LEVEL_3_1", LEVEL_2_2_ID));
        LEVEL_3_2_ID = nodeService.save(new CreateNodeCommand("LEVEL_3_2", "Description of LEVEL_3_2", LEVEL_2_2_ID));

        LEVEL_4_0_ID = nodeService.save(new CreateNodeCommand("LEVEL_4_0", "Description of LEVEL_4_0", LEVEL_3_0_ID));
        LEVEL_4_1_ID = nodeService.save(new CreateNodeCommand("LEVEL_4_1", "Description of LEVEL_4_1", LEVEL_3_0_ID));
        LEVEL_4_2_ID = nodeService.save(new CreateNodeCommand("LEVEL_4_2", "Description of LEVEL_4_2", LEVEL_3_2_ID));
        LEVEL_4_3_ID = nodeService.save(new CreateNodeCommand("LEVEL_4_3", "Description of LEVEL_4_3", "fat_cat.png", LEVEL_3_2_ID));
    }
}
