package com.qorvia.accountservice.config;

import com.qorvia.accountservice.model.organizer.Organizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomOrganizerDetails implements UserDetails {

    private final Organizer organizer;

    public CustomOrganizerDetails(Organizer organizer) {
        this.organizer = organizer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + organizer.getRoles().toString()));
    }

    @Override
    public String getPassword() {
        return organizer.getPassword();
    }

    @Override
    public String getUsername() {
        return organizer.getEmail();
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
}
