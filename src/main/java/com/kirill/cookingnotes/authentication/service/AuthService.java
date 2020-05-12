package com.kirill.cookingnotes.authentication.service;

import com.kirill.cookingnotes.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {
    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken);
    }

    public Long getAuthenticatedUserId() {
        if (!isAuthenticated()) {
            return null;
        }
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userPrincipal.getId();
    }
}
