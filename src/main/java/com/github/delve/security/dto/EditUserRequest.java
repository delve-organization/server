package com.github.delve.security.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EditUserRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
