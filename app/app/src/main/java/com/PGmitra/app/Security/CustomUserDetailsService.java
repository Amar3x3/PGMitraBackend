package com.PGmitra.app.Security;

import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Repository.TenantRepo;
import com.PGmitra.app.Repository.VenderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private VenderRepo ownerRepository;

    @Autowired
    private TenantRepo tenantRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Attempting to load user with username: {}", username);

        // Try to find owner first
        var owner = ownerRepository.findByUsername(username);
        if (owner.isPresent()) {
            logger.debug("Found owner with username: {}", username);
            return new User(
                owner.get().getUsername(),
                owner.get().getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_OWNER"))
            );
        }

        // If not found as owner, try to find as tenant
        var tenant = tenantRepository.findByUsername(username);
        if (tenant.isPresent()) {
            logger.debug("Found tenant with username: {}", username);
            return new User(
                tenant.get().getUsername(),
                tenant.get().getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_TENANT"))
            );
        }

        logger.debug("No user found with username: {}", username);
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
} 