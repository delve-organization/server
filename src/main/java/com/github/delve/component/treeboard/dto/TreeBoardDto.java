package com.github.delve.component.treeboard.dto;

public class TreeBoardDto {

    public final Long id;
    public final Long treeId;
    public final String title;
    public final String description;
    public final String imageUrl;
    public final String color;
    public final boolean editable;

    public TreeBoardDto(final Long id, final Long treeId, final String title, final String description, final String imageUrl, final String color, final boolean editable) {
        this.id = id;
        this.treeId = treeId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.color = color;
        this.editable = editable;
    }
}
