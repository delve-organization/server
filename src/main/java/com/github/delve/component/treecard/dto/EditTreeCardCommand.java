package com.github.delve.component.treecard.dto;

import com.github.delve.common.domain.Accessibility;

public class EditTreeCardCommand {

    public final Long treeCardId;
    public final Long treeId;
    public final String title;
    public final String description;
    public final String imageName;
    public final String color;
    public final Accessibility accessibility;

    public EditTreeCardCommand(final Long treeCardId, final Long treeId, final String title, final String description, final String imageName, final String color, final Accessibility accessibility) {
        this.treeCardId = treeCardId;
        this.treeId = treeId;
        this.title = title;
        this.description = description;
        this.imageName = imageName;
        this.color = color;
        this.accessibility = accessibility;
    }
}
