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

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private VenderRepo ownerRepository;

    @Autowired
    private TenantRepo tenantRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try to find owner first
        var owner = ownerRepository.findByEmail(email);
        if (owner.isPresent()) {
            return new User(
                owner.get().getEmail(),
                owner.get().getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_OWNER"))
            );
        }

        // If not found as owner, try to find as tenant
        var tenant = tenantRepository.findByEmail(email);
        if (tenant.isPresent()) {
            return new User(
                tenant.get().getEmail(),
                tenant.get().getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_TENANT"))
            );
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
} 