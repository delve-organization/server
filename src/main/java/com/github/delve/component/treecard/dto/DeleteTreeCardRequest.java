package com.github.delve.component.treecard.dto;

import javax.validation.constraints.NotNull;

public class DeleteTreeCardRequest {

    @NotNull
    private Long treeCardId;

    public Long getTreeCardId() {
        return treeCardId;
    }

    public void setTreeCardId(final Long treeCardId) {
        this.treeCardId = treeCardId;
    }
}
