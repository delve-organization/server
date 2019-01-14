package com.github.delve.integrationtest.node.util;

import com.github.delve.component.node.repository.NodeRepository;
import com.github.delve.component.node.service.NodeService;
import com.github.delve.dev.NodeTestData;
import com.github.delve.integrationtest.util.basedata.BaseData;
import org.springframework.beans.factory.annotation.Autowired;

public class NodeBaseData implements BaseData {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private NodeRepository nodeRepository;

    @Override
    public void load() {
        NodeTestData.createTestData(nodeService);
    }

    @Override
    public void unload() {
        nodeRepository.deleteAll();
    }
}
