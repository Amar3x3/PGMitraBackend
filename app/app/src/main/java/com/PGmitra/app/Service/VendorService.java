package com.PGmitra.app.Service;

import com.PGmitra.app.DTO.OwnerDTO;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Vendor;
import com.PGmitra.app.Exception.ResourceAlreadyExistsException;
import com.PGmitra.app.Repository.VenderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorService {
    @Autowired
    private VenderRepo ownerRepository;

    public Owner createVendor(OwnerDTO request) throws ResourceAlreadyExistsException{
        if (ownerRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResourceAlreadyExistsException("Username " + request.getUsername() + " already taken!");
        }
        if (ownerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email " + request.getEmail() + " already registered!");
        }

        Owner owner = new Owner();
        owner.setName(request.getName());
        owner.setUsername(request.getUsername());
        owner.setPassword(request.getPassword()); // Hash the password
        owner.setPhone(request.getPhone());
        owner.setEmail(request.getEmail());

        return ownerRepository.save(owner);
    }


    public boolean loginOwner(String username, String password){
        Optional<Owner> owner = ownerRepository.findByUsername(username);
        if(owner.isEmpty()) return false;
        if(owner.get().getPassword().equals(password)) return true;
        return false;
    }
}
