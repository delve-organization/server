package com.github.delve.integrationtest.treecard.util;

import com.github.delve.common.domain.Accessibility;
import com.github.delve.component.treecard.dto.TreeCardDto;
import com.github.delve.integrationtest.util.matcher.DelveTypeSafeMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.github.delve.integrationtest.util.matcher.DelveMatcher.is;
import static org.hamcrest.Matchers.any;

public class TreeCardDtoMatcher extends DelveTypeSafeMatcher<TreeCardDto> {

    private Matcher<Long> id = any(Long.class);
    private Matcher<Long> treeId = any(Long.class);
    private Matcher<String> title = any(String.class);
    private Matcher<String> description = any(String.class);
    private Matcher<String> image = any(String.class);
    private Matcher<String> imageUrl = any(String.class);
    private Matcher<String> color = any(String.class);
    private Matcher<Accessibility> accessibility = any(Accessibility.class);
    private Matcher<Boolean> editable = any(Boolean.class);
    private Matcher<String> ownerName = any(String.class);

    public static TreeCardDtoMatcher treeCardDto() {
        return new TreeCardDtoMatcher();
    }

    @Override
    protected boolean matchesSafely(final TreeCardDto item) {
        return id.matches(item.id) &&
                treeId.matches(item.treeId) &&
                title.matches(item.title) &&
                description.matches(item.description) &&
                image.matches(item.image) &&
                imageUrl.matches(item.imageUrl) &&
                color.matches(item.color) &&
                accessibility.matches(item.accessibility) &&
                editable.matches(item.editable) &&
                ownerName.matches(item.ownerName);
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
        this.image.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.imageUrl.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.color.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.accessibility.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.editable.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.ownerName.describeTo(expectedDescription);
    }

    @Override
    protected void describeActual(final TreeCardDto item, final Description actualDescription) {
        actualDescription
                .appendText("id: ").appendValue(item.id).appendText(", ")
                .appendText("treeId: ").appendValue(item.treeId).appendText(", ")
                .appendText("title: ").appendValue(item.title)
                .appendText("description: ").appendValue(item.description)
                .appendText("image: ").appendValue(item.image)
                .appendText("imageUrl: ").appendValue(item.imageUrl)
                .appendText("color: ").appendValue(item.color)
                .appendText("accessibility: ").appendValue(item.accessibility)
                .appendText("editable: ").appendValue(item.editable)
                .appendText("ownerName: ").appendValue(item.ownerName);
    }

    public TreeCardDtoMatcher hasId(final Long id) {
        this.id = is("id", id);
        return this;
    }

    public TreeCardDtoMatcher hasTreeId(final Long treeId) {
        this.treeId = is("treeId", treeId);
        return this;
    }

    public TreeCardDtoMatcher hasTitle(final String title) {
        this.title = is("title", title);
        return this;
    }

    public TreeCardDtoMatcher hasDescription(final String description) {
        this.description = is("description", description);
        return this;
    }

    public TreeCardDtoMatcher hasImage(final String image) {
        this.image = is("image", image);
        return this;
    }

    public TreeCardDtoMatcher hasImageUrl(final String imageUrl) {
        this.imageUrl = is("imageUrl", imageUrl);
        return this;
    }

    public TreeCardDtoMatcher hasColor(final String color) {
        this.color = is("color", color);
        return this;
    }

    public TreeCardDtoMatcher hasAccessibility(final Accessibility accessibility) {
        this.accessibility = is("accessibility", accessibility);
        return this;
    }

    public TreeCardDtoMatcher isEditable(final Boolean editable) {
        this.editable = is("editable", editable);
        return this;
    }

    public TreeCardDtoMatcher hasOwnerName(final String ownerName) {
        this.ownerName = is("ownerName", ownerName);
        return this;
    }
}
