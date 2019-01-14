package com.github.delve.integrationtest.node.util;

import com.github.delve.component.node.dto.NodeDto;
import com.github.delve.integrationtest.util.matcher.DelveMatcher;
import com.github.delve.integrationtest.util.matcher.DelveTypeSafeMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.any;

public class NodeDtoMatcher extends DelveTypeSafeMatcher<NodeDto> {

    private Matcher<Long> id = any(Long.class);
    private Matcher<String> title = any(String.class);
    private Matcher<String> description = any(String.class);
    private Matcher<String> imageUrl = any(String.class);

    private NodeDtoMatcher() {
    }

    public static NodeDtoMatcher nodeDto() {
        return new NodeDtoMatcher();
    }

    @Override
    protected boolean matchesSafely(final NodeDto item) {
        return id.matches(item.id) &&
                title.matches(item.title) &&
                description.matches(item.description)&&
                imageUrl.matches(item.imageUrl);
    }

    @Override
    protected void describeExpected(final Description expectedDescription) {
        this.id.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.title.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.description.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.imageUrl.describeTo(expectedDescription);
    }

    @Override
    protected void describeActual(final NodeDto item, final Description actualDescription) {
        actualDescription
                .appendText("id: ").appendValue(item.id).appendText(", ")
                .appendText("title: ").appendValue(item.title).appendText(", ")
                .appendText("description: ").appendValue(item.description).appendText(", ")
                .appendText("imageUrl: ").appendValue(item.imageUrl).appendText(", ");
    }

    public NodeDtoMatcher hasId(final Long id) {
        this.id = DelveMatcher.is("id", id);
        return this;
    }

    public NodeDtoMatcher hasTitle(final String title) {
        this.title = DelveMatcher.is("title", title);
        return this;
    }

    public NodeDtoMatcher hasDescription(final String description) {
        this.description = DelveMatcher.is("description", description);
        return this;
    }

    public NodeDtoMatcher hasImageUrl(final String imageUrl) {
        this.imageUrl = DelveMatcher.is("imageUrl", imageUrl);
        return this;
    }
}
