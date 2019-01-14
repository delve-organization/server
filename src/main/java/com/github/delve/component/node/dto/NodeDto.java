package com.github.delve.component.node.dto;

import java.util.List;

public class NodeDto {

    public final Long id;
    public final String title;
    public final String description;
    public final String imageUrl;
    public final List<NodeDto> children;

    public NodeDto(final Long id, final String title, final String description, final String imageUrl, final List<NodeDto> children) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.children = children;
    }
}
