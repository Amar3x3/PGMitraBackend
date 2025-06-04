package com.PGmitra.app.Service;

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

    public Property createNewProperty(Property property){
        Optional <Owner> owner = ownerRepo.findById(property.getOwner().getId());
        owner.get().getProperties().add(property);

        return propertyRepo.save(property);
    }
    public Optional<Property> getPropertyById(Long id){
        return propertyRepo.findById(id);
    }
}
