package com.github.delve.component.node.dto;

public class CreateNodeCommand {

    public final String title;
    public final String description;
    public final String imageName;
    public final Long parentId;

    public CreateNodeCommand(final String title, final String description, final String imageName, final Long parentId) {
        this.title = title;
        this.description = description;
        this.parentId = parentId;
        this.imageName = imageName;
    }

    public CreateNodeCommand(final String title, final String description, final String imageName) {
        this(title, description, imageName, null);
    }

    public CreateNodeCommand(final String title, final String description, final Long parentId) {
        this(title, description, null, parentId);
    }

    public CreateNodeCommand(final String title, final String description) {
        this(title, description, null, null);
    }
}
