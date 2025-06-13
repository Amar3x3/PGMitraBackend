package com.PGmitra.app.Security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User{
    private final Long userId; // The crucial custom field for PGMitra

    /**
     * Constructor for CustomUserDetails.
     * @param userId The unique identifier for the user.
     * @param username The username used for authentication.
     * @param password The encoded password.
     * @param authorities A collection of GrantedAuthority objects representing the user's roles/permissions.
     */
    public CustomUserDetails(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities); // Call super constructor to initialize UserDetails properties
        this.userId = userId; // Initialize our custom userId
    }

    /**
     * Retrieves the unique ID of the user.
     * @return The user's ID.
     */
    public Long getUserId() {
        return userId;
    }

    // All other UserDetails methods (getUsername, getPassword, isEnabled, etc.)
    // are automatically inherited from org.springframework.security.core.userdetails.User.
    // No need to implement them unless custom logic is required.
}

