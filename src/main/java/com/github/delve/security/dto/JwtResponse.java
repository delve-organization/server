package com.github.delve.security.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(final String accessToken, final Long userId, final String username, final Collection<? extends GrantedAuthority> authorities) {
        this.token = accessToken;
        this.userId = userId;
        this.username = username;
        this.authorities = authorities;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(final String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(final String tokenType) {
        this.type = tokenType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
