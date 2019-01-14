package com.github.delve.component.node.dto;

public class CreateUserNodeRelationCommand {

    private Long nodeId;
    private Boolean visited;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(final Long nodeId) {
        this.nodeId = nodeId;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(final Boolean visited) {
        this.visited = visited;
    }
}
