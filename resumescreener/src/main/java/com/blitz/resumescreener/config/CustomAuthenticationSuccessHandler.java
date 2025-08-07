package com.blitz.resumescreener.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
        
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_EMPLOYEE")) {
            response.sendRedirect("/upload"); // Redirect employee to the upload page
        } else if (roles.contains("ROLE_EMPLOYER")) {
            response.sendRedirect("/employer-dashboard"); // Redirect employer to their dashboard
        } else {
            response.sendRedirect("/"); // Default redirect
        }
    }
}