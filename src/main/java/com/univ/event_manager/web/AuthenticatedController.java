package com.univ.event_manager.web;

import com.univ.event_manager.data.dto.security.AuthorizedUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticatedController {
    default AuthorizedUserDetails authPrincipal(Authentication auth) {
        Object principal = auth.getPrincipal();

        return (AuthorizedUserDetails) principal;
    }
}
