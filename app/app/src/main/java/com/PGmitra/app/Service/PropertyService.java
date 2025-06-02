package com.PGmitra.app.Service;

import com.PGmitra.app.Entity.Property;
import com.PGmitra.app.Repository.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepo propertyRepo;

    public Property createNewProperty(Property property){
        return propertyRepo.save(property);
    }
    public Optional<Property> getPropertyById(Long id){
        return propertyRepo.findById(id);
    }
}
