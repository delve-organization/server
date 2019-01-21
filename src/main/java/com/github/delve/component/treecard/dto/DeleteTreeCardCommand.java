package com.github.delve.component.treecard.dto;

public class DeleteTreeCardCommand {

    public Long treeCardId;

    public DeleteTreeCardCommand() {
    }

    public DeleteTreeCardCommand(final Long treeCardId) {
        this.treeCardId = treeCardId;
    }
}
