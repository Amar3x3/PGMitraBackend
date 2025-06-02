package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepo extends JpaRepository<Property, Long> {
    Optional<Property>findById(Long id);
}
