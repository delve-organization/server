package com.github.delve.integrationtest.treecard.util;

import com.github.delve.component.treecard.repository.TreeCardRepository;
import com.github.delve.component.treecard.service.TreeCardService;
import com.github.delve.dev.TreeCardTestData;
import com.github.delve.integrationtest.tree.util.TreeBaseData;
import com.github.delve.integrationtest.util.basedata.BaseData;
import org.springframework.beans.factory.annotation.Autowired;

public class TreeCardBaseData implements BaseData {

    @Autowired
    private TreeCardService treeCardService;
    @Autowired
    private TreeCardRepository treeCardRepository;

    @Override
    public void load() {
        TreeCardTestData.createTestData(treeCardService);
    }

    @Override
    public void unload() {
        treeCardRepository.deleteAll();
    }

    @Override
    public Class<? extends BaseData> dependsOn() {
        return TreeBaseData.class;
    }
}
