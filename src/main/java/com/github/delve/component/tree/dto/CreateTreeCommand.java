package com.github.delve.component.tree.dto;

import com.github.delve.common.domain.Accessibility;

public class CreateTreeCommand {

    public final Long rootNodeId;
    public final String title;
    public final Accessibility accessibility;

    public CreateTreeCommand(final Long rootNodeId, final String title, final Accessibility accessibility) {
        this.rootNodeId = rootNodeId;
        this.title = title;
        this.accessibility = accessibility;
    }
}
