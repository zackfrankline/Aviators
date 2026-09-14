package com.avaitor.subscription_service.security;

import com.avaitor.subscription_service.model.Role;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityUtility {
    private Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UUID getCurrentUserId(){
        Authentication auth = getAuthentication();
        if(auth != null){
            return (UUID)auth.getPrincipal();
        }
        return null;
    }

    public boolean isCurrentUserAdmin(){
        Authentication auth = getAuthentication();
        if(auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken){
            return false;
        }
        return auth.getAuthorities().stream()
                .anyMatch( authority ->
                        authority.getAuthority().equals(Role.ROLE_ADMIN.toString()));
    }

    public boolean isCurrentUserAudience(){
        Authentication auth = getAuthentication();
        if(auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken){
            return false;
        }
        return auth.getAuthorities().stream()
                .anyMatch( authority ->
                        authority.getAuthority().equals(Role.ROLE_AUDIENCE.toString()));
    }
}
