package com.github.delve.component.node.dto;

public class UserNodeRelationDto {

    public final Long nodeId;
    public final Boolean visited;

    public UserNodeRelationDto(final Long nodeId, final Boolean visited) {
        this.nodeId = nodeId;
        this.visited = visited;
    }
}
