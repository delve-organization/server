package com.github.delve.integrationtest.node.util;

import com.github.delve.component.node.dto.UserNodeRelationDto;
import com.github.delve.integrationtest.util.matcher.DelveMatcher;
import com.github.delve.integrationtest.util.matcher.DelveTypeSafeMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.any;

public class UserNodeRelationDtoMatcher extends DelveTypeSafeMatcher<UserNodeRelationDto> {

    private Matcher<Long> nodeId = any(Long.class);
    private Matcher<Boolean> visited = any(Boolean.class);

    private UserNodeRelationDtoMatcher() {
    }

    public static UserNodeRelationDtoMatcher userNodeRelationDtoMatcher() {
        return new UserNodeRelationDtoMatcher();
    }

    @Override
    protected boolean matchesSafely(final UserNodeRelationDto item) {
        return nodeId.matches(item.nodeId) &&
                visited.matches(item.visited);
    }

    @Override
    protected void describeExpected(final Description expectedDescription) {
        this.nodeId.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.visited.describeTo(expectedDescription);
    }

    @Override
    protected void describeActual(final UserNodeRelationDto item, final Description actualDescription) {
        actualDescription
                .appendText("nodeId: ").appendValue(item.nodeId).appendText(", ")
                .appendText("visited: ").appendValue(item.visited).appendText(", ");
    }

    public UserNodeRelationDtoMatcher hasNodeId(final Long nodeId) {
        this.nodeId = DelveMatcher.is("nodeId", nodeId);
        return this;
    }

    public UserNodeRelationDtoMatcher hasVisited(final Boolean visited) {
        this.visited = DelveMatcher.is("visited", visited);
        return this;
    }
}
