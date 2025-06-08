package com.PGmitra.app.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PGmitra.app.DTO.FeedbackDTO;
import com.PGmitra.app.DTO.OwnerDTO;
import com.PGmitra.app.DTO.TenantDTO;
import com.PGmitra.app.DTO.TenantProfileDTO;
import com.PGmitra.app.DTO.TenantUpdateDTO;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Exception.ResourceAlreadyExistsException;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Response.LoginRequest;
import com.PGmitra.app.Response.LoginResponse;
import com.PGmitra.app.Service.FeedbackService;
import com.PGmitra.app.Service.TenantService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/tenant")
public class TenantController {
    
    @Autowired
    private TenantService tenantService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/hello")
    public String hello(){
        return "hello from tenant api";
    }

    @PostMapping("/register")
    public ResponseEntity<Tenant> createNewTenant(@RequestBody TenantDTO tenantDTO) throws ResourceAlreadyExistsException {
        Tenant createdTenant = tenantService.createTenant(tenantDTO);
        return new ResponseEntity<>(createdTenant, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public LoginResponse loginTenant(@RequestBody LoginRequest loginRequest){
        boolean login = tenantService.loginTenant(loginRequest.username(), loginRequest.password());
        if(login)
            return new LoginResponse(loginRequest.username(), "login succesful", HttpStatus.OK);
        else  return new LoginResponse(loginRequest.username(), "no user found", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getTenantProfile(@PathVariable String username) {
        try {
            TenantProfileDTO profile = tenantService.getTenantProfile(username);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
        catch(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/profile/id/{tenantId}")
    public ResponseEntity<?> getTenantProfile(@PathVariable Long tenantId) {
        try {
            TenantProfileDTO profile = tenantService.getTenantProfile(tenantId);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
        catch(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    
    @PostMapping("/feedback")
    public ResponseEntity<Object> submitFeedback(@RequestBody FeedbackDTO dto, @RequestParam Long tenantId) { //To be replaced by authenticated ID later 
        try {
            feedbackService.submitFeedback(dto, tenantId);        
            return ResponseEntity.status(HttpStatus.CREATED).body("Complaint Registered Successfully!");
        }
        catch(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("update/{tenantId}")
    public ResponseEntity<Object> updateTenant(@PathVariable Long tenantId, @RequestBody TenantUpdateDTO dto) {
        try{
            tenantService.updateTenantDetails(tenantId, dto);
            return ResponseEntity.status(HttpStatus.OK).body("Details updated Successfully!");
        }
        catch(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }    
    }
    
}
