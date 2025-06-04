package com.PGmitra.app.Service;

import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Repository.TenantRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PGmitra.app.DTO.TenantDTO;
import com.PGmitra.app.DTO.TenantProfileDTO;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Exception.ResourceAlreadyExistsException;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Repository.TenantRepo;

@Service
public class TenantService {

    @Autowired
    private TenantRepo tenantRepository;

    public Tenant createTenant(TenantDTO dto) throws ResourceAlreadyExistsException {
        if (tenantRepository.findByUsername(dto.getUserName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Username " + dto.getUserName() + " already taken!");
        }

        if (tenantRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email " + dto.getEmail() + " already registered!");
        }

        Tenant tenant = new Tenant();
        tenant.setEmail(dto.getEmail());
        tenant.setName(dto.getName());
        tenant.setUsername(dto.getUserName());
        tenant.setPhone(dto.getPhoneNumber());
        tenant.setGender(dto.getGender());
        tenant.setFoodPreference(dto.getFoodPreference());
        tenant.setPassword(dto.getPassword());

        return tenantRepository.save(tenant);
    }

    public boolean loginTenant(String username, String password){
        Optional<Tenant> tenant = tenantRepository.findByUsername(username);
        if(tenant.isEmpty()) return false;
        if(tenant.get().getPassword().equals(password)) return true;
        return false;
    }

    public TenantProfileDTO getTenantProfile(String username) throws ResourceNotFoundException {
        Tenant tenant = tenantRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Tenant with username '" + username + "' not found"));
        return new TenantProfileDTO(tenant.getId(), tenant.getEmail(), tenant.getPhone(), tenant.getGender(), tenant.getFoodPreference());
    }

    

    

    public Optional<Tenant> getTenantById(long id){
        return tenantRepository.findById(id);
    }
}
