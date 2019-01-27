package com.github.delve.common.exception;

public class DelveException extends RuntimeException {

    public DelveException() {
    }

    public DelveException(final String message, final Object... args) {
        super(String.format(message, args));
    }

    public DelveException(final Throwable cause, final String message, final Object... args) {
        super(String.format(message, args), cause);
    }

    public DelveException(final Throwable cause) {
        super(cause);
    }
}
