package com.github.delve.integrationtest.util.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public abstract class DelveTypeSafeMatcher<T> extends TypeSafeMatcher<T> {

    @Override
    public void describeTo(final Description description) {
        describeExpected(description);
    }

    @Override
    protected void describeMismatchSafely(final T item, final Description mismatchDescription) {
        describeActual(item, mismatchDescription);
    }

    protected abstract void describeExpected(final Description expectedDescription);

    protected abstract void describeActual(final T item, final Description actualDescription);
}
