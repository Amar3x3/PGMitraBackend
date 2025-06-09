package com.PGmitra.app.Controller;

import com.PGmitra.app.DTO.OwnerDTO;
import com.PGmitra.app.DTO.TenantDTO;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Exception.ResourceAlreadyExistsException;
import com.PGmitra.app.Response.LoginRequest;
import com.PGmitra.app.Response.LoginResponse;
import com.PGmitra.app.Response.StatusAndMessageResponse;
import com.PGmitra.app.Security.JwtTokenProvider;
import com.PGmitra.app.Service.TenantService;
import com.PGmitra.app.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private TenantService tenantService;

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
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.username(),
                    loginRequest.password()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new LoginResponse(loginRequest.username(), jwt, HttpStatus.OK));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(loginRequest.username(), "Invalid credentials", HttpStatus.UNAUTHORIZED));
        }
    }
} 