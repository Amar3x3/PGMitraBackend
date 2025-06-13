package com.PGmitra.app.Controller;

import com.PGmitra.app.DTO.OwnerDTO;
import com.PGmitra.app.DTO.TenantDTO;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Exception.ResourceAlreadyExistsException;
import com.PGmitra.app.Response.LoginRequest;
import com.PGmitra.app.Response.LoginResponse;
import com.PGmitra.app.Response.StatusAndMessageResponse;
import com.PGmitra.app.Security.CustomUserDetailsService;
import com.PGmitra.app.Security.JwtTokenProvider;
import com.PGmitra.app.Service.TenantService;
import com.PGmitra.app.Service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register/owner")
    public ResponseEntity<Object> registerOwner(@RequestBody OwnerDTO ownerDTO) {
        try {
            Owner createdOwner = vendorService.createVendor(ownerDTO);
            return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
        } catch (ResourceAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new StatusAndMessageResponse(HttpStatus.CONFLICT, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    @PostMapping("/register/tenant")
    public ResponseEntity<Object> registerTenant(@RequestBody TenantDTO tenantDTO) {
        try {
            Tenant createdTenant = tenantService.createTenant(tenantDTO);
            return new ResponseEntity<>(createdTenant, HttpStatus.CREATED);
        } catch (ResourceAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new StatusAndMessageResponse(HttpStatus.CONFLICT, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        try {
            logger.debug("Attempting login for user: {}", loginRequest.username());

            // Create authentication token
            UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
            logger.debug("Created authentication token for user: {}", loginRequest.username());

            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            logger.debug("Authentication successful for user: {}", loginRequest.username());

            // Set authentication in security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security context updated for user: {}", loginRequest.username());

            // Generate tokens
            String accessToken = tokenProvider.generateAccessToken(authentication);
            String refreshToken = tokenProvider.generateRefreshToken(authentication);
            logger.debug("Generated tokens for user: {}", loginRequest.username());

            // Return success response with tokens
            return ResponseEntity.ok(new LoginResponse(loginRequest.username(), accessToken, refreshToken, HttpStatus.OK));
        } catch (Exception ex) {
            logger.error("Login failed for user: {}. Error: {}", loginRequest.username(), ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(loginRequest.username(), HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        try {
            if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
                refreshToken = refreshToken.substring(7);
            }

            if (!tokenProvider.validateToken(refreshToken) || !tokenProvider.isRefreshToken(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse(null, HttpStatus.UNAUTHORIZED, "Invalid refresh token"));
            }

            String username = tokenProvider.getUsernameFromJWT(refreshToken);
            logger.debug("Refreshing token for user: {}", username);

            // Load UserDetails object using your CustomUserDetailsService
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Create authentication token with UserDetails as principal
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // Pass authorities too

            // Generate new tokens
            String newAccessToken = tokenProvider.generateAccessToken(authenticationToken);
            String newRefreshToken = tokenProvider.generateRefreshToken(authenticationToken); // If refresh token should also be refreshed

            return ResponseEntity.ok(new LoginResponse(username, newAccessToken, newRefreshToken, HttpStatus.OK));

        } catch (UsernameNotFoundException ex) { // Catch specifically if the user isn't found
            logger.error("Token refresh failed. User not found: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, HttpStatus.UNAUTHORIZED, "User not found for refresh token."));
        }
        catch (Exception ex) {
            logger.error("Token refresh failed. Error: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, HttpStatus.UNAUTHORIZED, "Invalid refresh token or internal error."));
        }
    }
} 