package com.PGmitra.app.Service;

import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Repository.TenantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.PGmitra.app.DTO.TenantDTO;
import com.PGmitra.app.DTO.TenantProfileDTO;
import com.PGmitra.app.DTO.TenantUpdateDTO;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Exception.ResourceAlreadyExistsException;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Repository.TenantRepo;

@Service
public class TenantService {

    @Autowired
    private TenantRepo tenantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Tenant createTenant(TenantDTO dto) throws ResourceAlreadyExistsException {
        if (tenantRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ResourceAlreadyExistsException("Username " + dto.getUsername() + " already taken!");
        }

        if (tenantRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email " + dto.getEmail() + " already registered!");
        }

        Tenant tenant = new Tenant();
        tenant.setEmail(dto.getEmail());
        tenant.setName(dto.getName());
        tenant.setUsername(dto.getUsername());
        tenant.setPhone(dto.getPhonenumber());
        tenant.setGender(dto.getGender());

        tenant.setPassword(passwordEncoder.encode(dto.getPassword()));
        tenant.setAadharNumber(dto.getAadharNumber());
        tenant.setEmergencyContactName(dto.getEmergencyContactName());
        tenant.setEmergencyContactPhone(dto.getEmergencyContactPhone());
        tenant.setOccupation(dto.getOccupation());

        return tenantRepository.save(tenant);
    }

    public boolean loginTenant(String username, String password){
        Optional<Tenant> tenant = tenantRepository.findByUsername(username);
        if(tenant.isEmpty()) return false;
        return passwordEncoder.matches(password, tenant.get().getPassword());
    }

    public TenantProfileDTO getTenantProfile(String username) throws ResourceNotFoundException {
        Tenant tenant = tenantRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Tenant with username '" + username + "' not found"));

        Integer room_no = null;
        if (tenant.getRoom() != null) {
            room_no = tenant.getRoom().getRoom_no();
        }
        return new TenantProfileDTO(tenant.getId(), tenant.getEmail(), tenant.getName(), tenant.getPhone(), tenant.getGender(), tenant.getEmergencyContactName(), tenant.getEmergencyContactPhone(), tenant.getOccupation(), room_no, tenant.getAadharNumber());  //Remodelled, as foodPreference removed from tenantProfileDTO

     
    }

    public TenantProfileDTO getTenantProfile(Long tenantId) throws ResourceNotFoundException {
        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new ResourceNotFoundException("Tenant with username '" + tenantId + "' not found"));

        Integer room_no = null;
        if (tenant.getRoom() != null) {
            room_no = tenant.getRoom().getRoom_no();
        }
        return new TenantProfileDTO(tenant.getId(), tenant.getEmail(), tenant.getName(), tenant.getPhone(), tenant.getGender(), tenant.getEmergencyContactName(), tenant.getEmergencyContactPhone(), tenant.getOccupation(), room_no, tenant.getAadharNumber());


    }
    

    public Optional<Tenant> getTenantById(long id){
        return tenantRepository.findById(id);
    }

    public void updateTenantDetails(Long tenantId, TenantUpdateDTO dto) {
        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

        if(dto.getName()!=null) tenant.setName(dto.getName());
        if(dto.getPhone()!=null) tenant.setPhone(dto.getPhone());
        if(dto.getGender()!=null) tenant.setGender(dto.getGender());
        if(dto.getFoodPreference()!=null) tenant.setFoodPreference(dto.getFoodPreference());
        if(dto.getEmergencyContactName()!=null) tenant.setEmergencyContactName(dto.getEmergencyContactName());
        if(dto.getEmergencyContactPhone()!=null) tenant.setEmergencyContactPhone(dto.getEmergencyContactPhone());
        if(dto.getOccupation()!=null) tenant.setOccupation(dto.getOccupation());

        tenantRepository.save(tenant);
    }

    public List<Tenant> getAllTenants(Long ownerId) {
        try{
            return tenantRepository.findAllByOwner_Id(ownerId);
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    public List<Tenant> getTenantsByRoom(Long roomId) throws ResourceNotFoundException {
        List<Tenant> tenants = tenantRepository.findAllByRoom_Id(roomId);
        if (tenants.isEmpty()) {
            return Collections.emptyList();
        }
        return tenants;
    }
}
