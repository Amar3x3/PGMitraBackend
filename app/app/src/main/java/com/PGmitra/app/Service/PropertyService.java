package com.PGmitra.app.Service;

import com.PGmitra.app.DTO.PropertyDTO;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Property;
import com.PGmitra.app.Repository.PropertyRepo;
import com.PGmitra.app.Repository.VenderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepo propertyRepo;

    @Autowired
    private VenderRepo ownerRepo;

    public Property createNewProperty(PropertyDTO propertyDTO, Long id){
        Optional <Owner> owner = ownerRepo.findById(id);
        Property property = new Property();
        property.setAddress(propertyDTO.getAddress());
        property.setName(propertyDTO.getName());
        property.setOwner(owner.get());
        owner.get().getProperties().add(property);

        Property response =  propertyRepo.save(property);
        ownerRepo.save(owner.get());

        return response;
    }
    public Optional<Property> getPropertyById(Long id){
        return propertyRepo.findById(id);
    }
}
