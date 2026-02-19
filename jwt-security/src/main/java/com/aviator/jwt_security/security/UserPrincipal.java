package com.aviator.jwt_security.security;

import com.aviator.jwt_security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//this Class acts as a wrapper class for User Entity
public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user){
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().name());
        return List.of(grantedAuthority);
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true; // MUST be true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // MUST be true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // MUST be true
    }

    @Override
    public boolean isEnabled() {
        return true; // MUST be true
    }
}
