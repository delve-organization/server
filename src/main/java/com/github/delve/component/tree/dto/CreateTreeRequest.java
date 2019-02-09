package com.github.delve.component.tree.dto;

import com.github.delve.common.domain.Accessibility;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateTreeRequest {

    @NotNull
    private Long rootNodeId;

    @NotBlank
    private String title;

    @NotNull
    private Accessibility accessibility;

    public Long getRootNodeId() {
        return rootNodeId;
    }

    public void setRootNodeId(final Long rootNodeId) {
        this.rootNodeId = rootNodeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Accessibility getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(final Accessibility accessibility) {
        this.accessibility = accessibility;
    }
}
