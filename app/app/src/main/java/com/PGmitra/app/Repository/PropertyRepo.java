package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Feedback;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PropertyRepo extends JpaRepository<Property, Long> {
    Optional<Property>findById(Long id);
    List<Property> findAllByOwner(Owner owner);
}
