package com.aviator.content_servive.security;

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
        Authentication authentication = getAuthentication();
        if(authentication != null){
            return (UUID) authentication.getPrincipal();
        }
        return null;
    }

    public boolean isCurrentUserAdmin(){
        Authentication authentication = getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken){
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(
                        authority ->
                                authority.getAuthority().equals("ROLE_ADMIN")
                );
    }
}
