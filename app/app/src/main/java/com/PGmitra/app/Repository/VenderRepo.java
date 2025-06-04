package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VenderRepo extends JpaRepository<Owner, Long> {
    Optional<Owner> findByUsername(String username);
    Optional<Owner> findByEmail(String email);
}
