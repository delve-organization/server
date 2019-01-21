package com.github.delve.component.treecard.dto;

import com.github.delve.common.domain.Accessibility;

public class CreateTreeCardCommand {

    public Long treeId;
    public String title;
    public String description;
    public String imageName;
    public String color;
    public Accessibility accessibility;

    public CreateTreeCardCommand() {
    }

    public CreateTreeCardCommand(final Long treeId, final String title, final String description, final String imageName, final String color, final Accessibility accessibility) {
        this.treeId = treeId;
        this.title = title;
        this.description = description;
        this.imageName = imageName;
        this.color = color;
        this.accessibility = accessibility;
    }
}
