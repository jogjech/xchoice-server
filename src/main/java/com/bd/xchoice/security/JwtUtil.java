package com.bd.xchoice.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Utility class for retrieving info from jwt token.
 */
public final class JwtUtil {

    /**
     * Get user email from JWT token. The email is configured in Auth0 rule.
     *
     * @return user email
     */
    public static String getUserEmail() {
        final Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (String) jwt.getClaims().get("https://x-choice-server.com/email");
    }

    private JwtUtil() {}
}
