package com.github.delve.security.service.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.delve.security.domain.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.delve.security.domain.RoleName.ROLE_ADMIN;
import static com.github.delve.security.domain.RoleName.ROLE_USER;

public class UserPrinciple implements UserDetails {

    public static final SimpleGrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority(ROLE_ADMIN.name());
    public static final SimpleGrantedAuthority USER_AUTHORITY = new SimpleGrantedAuthority(ROLE_USER.name());

    private static final long serialVersionUID = 1L;
    private static final List<SimpleGrantedAuthority> AVAILABLE_AUTHORITIES = Arrays.asList(ADMIN_AUTHORITY, USER_AUTHORITY);

    private final Long id;
    private final String name;
    private final String username;
    private final String email;
    @JsonIgnore
    private final String password;
    private final Collection<SimpleGrantedAuthority> authorities;

    public UserPrinciple(final Long id, final String name,
                         final String username, final String email, final String password,
                         final Collection<SimpleGrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrinciple build(final User user) {
        final Set<String> userRoles = user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet());
        final List<SimpleGrantedAuthority> authorities = AVAILABLE_AUTHORITIES.stream()
                .filter(authority -> userRoles.contains(authority.getAuthority()))
                .collect(Collectors.toList());

        return new UserPrinciple(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}
