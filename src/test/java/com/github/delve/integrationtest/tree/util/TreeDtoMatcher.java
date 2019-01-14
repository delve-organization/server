package com.github.delve.integrationtest.tree.util;

import com.github.delve.common.domain.Accessibility;
import com.github.delve.component.tree.dto.TreeDto;
import com.github.delve.integrationtest.util.matcher.DelveMatcher;
import com.github.delve.integrationtest.util.matcher.DelveTypeSafeMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.any;

public class TreeDtoMatcher extends DelveTypeSafeMatcher<TreeDto> {

    private Matcher<Long> id = any(Long.class);
    private Matcher<Long> rootNodeId = any(Long.class);
    private Matcher<String> title = any(String.class);
    private Matcher<Boolean> editable = any(Boolean.class);
    private Matcher<Accessibility> accessibility = any(Accessibility.class);

    private TreeDtoMatcher() {
    }

    public static TreeDtoMatcher treeDto() {
        return new TreeDtoMatcher();
    }

    @Override
    protected boolean matchesSafely(final TreeDto item) {
        return id.matches(item.id) &&
                rootNodeId.matches(item.rootNodeId) &&
                title.matches(item.title) &&
                editable.matches(item.editable) &&
                accessibility.matches(item.accessibility);
    }

    @Override
    protected void describeExpected(final Description expectedDescription) {
        this.id.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.rootNodeId.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.title.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.editable.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.accessibility.describeTo(expectedDescription);
    }

    @Override
    protected void describeActual(final TreeDto item, final Description actualDescription) {
        actualDescription
                .appendText("id: ").appendValue(item.id).appendText(", ")
                .appendText("rootNodeId: ").appendValue(item.rootNodeId).appendText(", ")
                .appendText("title: ").appendValue(item.title)
                .appendText("editable: ").appendValue(item.editable)
                .appendText("accessibility: ").appendValue(item.accessibility);
    }

    public TreeDtoMatcher hasId(final Long id) {
        this.id = DelveMatcher.is("id", id);
        return this;
    }

    public TreeDtoMatcher hasRootNodeId(final Long rootNodeId) {
        this.rootNodeId = DelveMatcher.is("rootNodeId", rootNodeId);
        return this;
    }

    public TreeDtoMatcher hasTitle(final String title) {
        this.title = DelveMatcher.is("title", title);
        return this;
    }

    public TreeDtoMatcher isEditable(final Boolean editable) {
        this.editable = DelveMatcher.is("editable", editable);
        return this;
    }

    public TreeDtoMatcher hasAccessibility(final Accessibility accessibility) {
        this.accessibility = DelveMatcher.is("accessibility", accessibility);
        return this;
    }
}
