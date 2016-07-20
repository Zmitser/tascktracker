package com.codexsoft.webapp.model;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_DEVELOPER,
    ROLE_MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
