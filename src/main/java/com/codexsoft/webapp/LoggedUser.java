package com.codexsoft.webapp;

import com.codexsoft.webapp.model.Role;
import com.codexsoft.webapp.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import static java.util.Objects.requireNonNull;
public class LoggedUser implements UserDetails, Serializable {

    private Long userId;
    private String email;
    private String password;
    private String username;
    private Role role;
    private Boolean enabled;


    public LoggedUser(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.username = user.getUsername();
        this.enabled = user.getIsEnabled();
        this.userId = user.getUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    public static LoggedUser unSafeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object acc = auth.getPrincipal();
        return (acc instanceof LoggedUser) ?
                (LoggedUser) acc : null;
    }

    public static LoggedUser get() {
        LoggedUser acc = unSafeGet();
        requireNonNull(acc, "No authorized account found");
        return acc;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }



    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}