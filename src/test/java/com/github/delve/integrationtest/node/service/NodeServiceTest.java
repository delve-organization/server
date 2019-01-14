package com.github.delve.integrationtest.node.service;

import com.github.delve.component.node.dto.CreateNodeCommand;
import com.github.delve.component.node.dto.NodeDto;
import com.github.delve.component.node.service.NodeService;
import com.github.delve.integrationtest.SpringBootTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.delve.integrationtest.node.util.NodeDtoMatcher.nodeDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class NodeServiceTest extends SpringBootTestBase {

    @Autowired
    private NodeService nodeService;

    @Test
    public void saveOneAndGet() {
        final Long savedNodeId = nodeService.save(new CreateNodeCommand("title", "description"));
        final NodeDto savedNode = nodeService.getNodesFromRoot(savedNodeId);

        assertThat(savedNode, nodeDto()
                .hasId(savedNodeId)
                .hasTitle("title")
                .hasDescription("description")
                .hasImageUrl(null));

        assertEquals(savedNode.children.size(), 0);
    }

    @Test
    public void saveHierarchicalAndGet() {
        final Long rootNodeId = nodeService.save(new CreateNodeCommand("root", "description"));
        final Long childNode1 = nodeService.save(new CreateNodeCommand("child1", "description", rootNodeId));
        final Long childNode2 = nodeService.save(new CreateNodeCommand("child2", "description", rootNodeId));

        final NodeDto savedRootNode = nodeService.getNodesFromRoot(rootNodeId);

        assertThat(savedRootNode, nodeDto()
                .hasId(rootNodeId)
                .hasTitle("root")
                .hasDescription("description")
                .hasImageUrl(null));
        assertEquals(savedRootNode.children.size(), 2);

        final NodeDto savedChildNode1 = savedRootNode.children.get(0);
        final NodeDto savedChildNode2 = savedRootNode.children.get(1);

        assertThat(savedChildNode1, nodeDto()
                .hasId(childNode1)
                .hasTitle("child1")
                .hasDescription("description")
                .hasImageUrl(null));
        assertThat(savedChildNode2, nodeDto()
                .hasId(childNode2)
                .hasTitle("child2")
                .hasDescription("description")
                .hasImageUrl(null));
    }

    @Test(expected = IllegalStateException.class)
    public void rootNodeNotFound() {
        nodeService.getNodesFromRoot(-1L);
    }
}
