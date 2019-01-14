package com.github.delve.integrationtest.util.matcher;

import org.hamcrest.Description;
import org.hamcrest.core.IsEqual;

public class DelveMatcher<T> extends IsEqual<T> {

    private final String name;
    private final T value;

    private DelveMatcher(final String name, final T value) {
        super(value);
        this.name = name;
        this.value = value;
    }

    public static <T> DelveMatcher<T> is(final String name, T value) {
        return new DelveMatcher<>(name, value);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(name).appendText(": ").appendValue(value);
    }
}
