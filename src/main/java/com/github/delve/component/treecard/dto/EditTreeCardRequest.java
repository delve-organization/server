package com.github.delve.component.treecard.dto;

import com.github.delve.common.domain.Accessibility;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditTreeCardRequest {

    @NotNull
    private Long treeCardId;

    @NotNull
    private Long treeId;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String imageName;

    @NotBlank
    private String color;

    @NotNull
    private Accessibility accessibility;

    public Long getTreeCardId() {
        return treeCardId;
    }

    public void setTreeCardId(final Long treeCardId) {
        this.treeCardId = treeCardId;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(final Long treeId) {
        this.treeId = treeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(final String imageName) {
        this.imageName = imageName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public Accessibility getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(final Accessibility accessibility) {
        this.accessibility = accessibility;
    }
}
