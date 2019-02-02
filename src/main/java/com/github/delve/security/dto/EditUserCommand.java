package com.github.delve.security.dto;

public class EditUserCommand {

    public final Long userId;
    public final String name;

    public EditUserCommand(final Long userId, final String name) {
        this.userId = userId;
        this.name = name;
    }
}
