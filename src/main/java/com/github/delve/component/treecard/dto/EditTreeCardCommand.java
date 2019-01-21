package com.github.delve.component.treecard.dto;

import com.github.delve.common.domain.Accessibility;

public class EditTreeCardCommand {

    public Long treeCardId;
    public Long treeId;
    public String title;
    public String description;
    public String imageName;
    public String color;
    public Accessibility accessibility;

    public EditTreeCardCommand() {
    }

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
