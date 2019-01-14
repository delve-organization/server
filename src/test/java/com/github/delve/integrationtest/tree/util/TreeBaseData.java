package com.github.delve.integrationtest.tree.util;

import com.github.delve.component.tree.repository.TreeRepository;
import com.github.delve.component.tree.service.TreeService;
import com.github.delve.dev.TreeTestData;
import com.github.delve.integrationtest.node.util.NodeBaseData;
import com.github.delve.integrationtest.util.basedata.BaseData;
import org.springframework.beans.factory.annotation.Autowired;

public class TreeBaseData implements BaseData {

    @Autowired
    private TreeService treeService;
    @Autowired
    private TreeRepository treeRepository;

    @Override
    public void load() {
        TreeTestData.createTestData(treeService);
    }

    @Override
    public void unload() {
        treeRepository.deleteAll();
    }

    @Override
    public Class<? extends BaseData> dependsOn() {
        return NodeBaseData.class;
    }
}
