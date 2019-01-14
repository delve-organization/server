package com.github.delve.integrationtest.treeboard.util;

import com.github.delve.component.treeboard.dto.TreeBoardDto;
import com.github.delve.integrationtest.util.matcher.DelveMatcher;
import com.github.delve.integrationtest.util.matcher.DelveTypeSafeMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.any;

public class TreeBoardDtoMatcher extends DelveTypeSafeMatcher<TreeBoardDto> {

    private Matcher<Long> id = any(Long.class);
    private Matcher<Long> treeId = any(Long.class);
    private Matcher<String> title = any(String.class);
    private Matcher<String> description = any(String.class);
    private Matcher<String> imageUrl = any(String.class);
    private Matcher<String> color = any(String.class);
    private Matcher<Boolean> editable = any(Boolean.class);

    public static TreeBoardDtoMatcher treeBoardDto() {
        return new TreeBoardDtoMatcher();
    }

    @Override
    protected boolean matchesSafely(final TreeBoardDto item) {
        return id.matches(item.id) &&
                treeId.matches(item.treeId) &&
                title.matches(item.title) &&
                description.matches(item.description) &&
                imageUrl.matches(item.imageUrl) &&
                color.matches(item.color) &&
                editable.matches(item.editable);
    }

    @Override
    protected void describeExpected(final Description expectedDescription) {
        this.id.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.treeId.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.title.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.description.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.imageUrl.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.color.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.editable.describeTo(expectedDescription);
    }

    @Override
    protected void describeActual(final TreeBoardDto item, final Description actualDescription) {
        actualDescription
                .appendText("id: ").appendValue(item.id).appendText(", ")
                .appendText("treeId: ").appendValue(item.treeId).appendText(", ")
                .appendText("title: ").appendValue(item.title)
                .appendText("description: ").appendValue(item.description)
                .appendText("imageUrl: ").appendValue(item.imageUrl)
                .appendText("color: ").appendValue(item.color)
                .appendText("editable: ").appendValue(item.editable);
    }

    public TreeBoardDtoMatcher hasId(final Long id) {
        this.id = DelveMatcher.is("id", id);
        return this;
    }

    public TreeBoardDtoMatcher hasTreeId(final Long treeId) {
        this.treeId = DelveMatcher.is("treeId", treeId);
        return this;
    }

    public TreeBoardDtoMatcher hasTitle(final String title) {
        this.title = DelveMatcher.is("title", title);
        return this;
    }

    public TreeBoardDtoMatcher hasDescription(final String description) {
        this.description = DelveMatcher.is("description", description);
        return this;
    }

    public TreeBoardDtoMatcher hasImageUrl(final String imageUrl) {
        this.imageUrl = DelveMatcher.is("imageUrl", imageUrl);
        return this;
    }

    public TreeBoardDtoMatcher hasColor(final String color) {
        this.color = DelveMatcher.is("color", color);
        return this;
    }

    public TreeBoardDtoMatcher isEditable(final Boolean editable) {
        this.editable = DelveMatcher.is("editable", editable);
        return this;
    }
}
