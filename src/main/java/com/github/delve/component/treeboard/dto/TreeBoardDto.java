package com.github.delve.component.treeboard.dto;

import com.github.delve.common.domain.Accessibility;

public class TreeBoardDto {

    public final Long id;
    public final Long treeId;
    public final String title;
    public final String description;
    public final String image;
    public final String imageUrl;
    public final String color;
    public final Accessibility accessibility;
    public final boolean editable;

    public TreeBoardDto(final Long id, final Long treeId, final String title, final String description, final String image,
                        final String imageUrl, final String color, final Accessibility accessibility, final boolean editable) {
        this.id = id;
        this.treeId = treeId;
        this.title = title;
        this.description = description;
        this.image = image;
        this.imageUrl = imageUrl;
        this.color = color;
        this.accessibility = accessibility;
        this.editable = editable;
    }
}
