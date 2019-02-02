package com.github.delve.component.treecard.dto;

public class DeleteTreeCardCommand {

    public final Long treeCardId;

    public DeleteTreeCardCommand(final Long treeCardId) {
        this.treeCardId = treeCardId;
    }
}
