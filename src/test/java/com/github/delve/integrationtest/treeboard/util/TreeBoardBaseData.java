package com.github.delve.integrationtest.treeboard.util;

import com.github.delve.component.treeboard.repository.TreeBoardRepository;
import com.github.delve.component.treeboard.service.TreeBoardService;
import com.github.delve.dev.TreeBoardTestData;
import com.github.delve.integrationtest.tree.util.TreeBaseData;
import com.github.delve.integrationtest.util.basedata.BaseData;
import org.springframework.beans.factory.annotation.Autowired;

public class TreeBoardBaseData implements BaseData {

    @Autowired
    private TreeBoardService treeBoardService;
    @Autowired
    private TreeBoardRepository treeBoardRepository;

    @Override
    public void load() {
        TreeBoardTestData.createTestData(treeBoardService);
    }

    @Override
    public void unload() {
        treeBoardRepository.deleteAll();
    }

    @Override
    public Class<? extends BaseData> dependsOn() {
        return TreeBaseData.class;
    }
}
