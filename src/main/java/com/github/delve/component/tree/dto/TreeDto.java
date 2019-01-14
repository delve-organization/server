package com.github.delve.component.tree.dto;

import com.github.delve.common.domain.Accessibility;

public class TreeDto {

    public final Long id;
    public final Long rootNodeId;
    public final String title;
    public final boolean editable;
    public final Accessibility accessibility;

    public TreeDto(final Long id, final Long rootNodeId, final String title, final boolean editable, final Accessibility accessibility) {
        this.id = id;
        this.rootNodeId = rootNodeId;
        this.title = title;
        this.editable = editable;
        this.accessibility = accessibility;
    }
}
