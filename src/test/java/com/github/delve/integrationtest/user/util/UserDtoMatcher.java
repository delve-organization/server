package com.github.delve.integrationtest.user.util;

import com.github.delve.integrationtest.util.matcher.DelveTypeSafeMatcher;
import com.github.delve.security.domain.RoleName;
import com.github.delve.security.dto.UserDto;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class UserDtoMatcher extends DelveTypeSafeMatcher<UserDto> {

    private Matcher<Long> id = any(Long.class);
    private Matcher<String> name = any(String.class);
    private Matcher<String> username = any(String.class);
    private Matcher<String> email = any(String.class);
    @SuppressWarnings("unchecked")
    private Matcher<Iterable<? extends RoleName>> roles = hasItems(new Matcher[]{any(RoleName.class)});

    private UserDtoMatcher() {
    }

    public static UserDtoMatcher userDto() {
        return new UserDtoMatcher();
    }

    @Override
    protected boolean matchesSafely(final UserDto item) {
        return id.matches(item.id) &&
                name.matches(item.name) &&
                username.matches(item.username) &&
                email.matches(item.email) &&
                roles.matches(item.roles);
    }

    @Override
    protected void describeExpected(final Description expectedDescription) {
        this.id.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.name.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.username.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.email.describeTo(expectedDescription);
        expectedDescription.appendText(", ");
        this.roles.describeTo(expectedDescription);
    }

    @Override
    protected void describeActual(final UserDto item, final Description actualDescription) {
        actualDescription
                .appendText("id: ").appendValue(item.id).appendText(", ")
                .appendText("name: ").appendValue(item.name).appendText(", ")
                .appendText("username: ").appendValue(item.username).appendText(", ")
                .appendText("email: ").appendValue(item.email).appendText(", ")
                .appendText("roles: ").appendValue(item.roles).appendText(", ");
    }

    public UserDtoMatcher hasId(final Long id) {
        this.id = is(id);
        return this;
    }

    public UserDtoMatcher hasName(final String name) {
        this.name = is(name);
        return this;
    }

    public UserDtoMatcher hasUsername(final String username) {
        this.username = is(username);
        return this;
    }

    public UserDtoMatcher hasEmail(final String email) {
        this.email = is(email);
        return this;
    }

    public UserDtoMatcher hasRoles(final RoleName... roles) {
        this.roles = containsInAnyOrder(roles);
        return this;
    }
}
