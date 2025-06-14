package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Tenant;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepo extends JpaRepository<Tenant, Integer> {
    Optional<Tenant> findByUsername(String username);
    Optional<Tenant> findByEmail(String email);
    Optional<Tenant> findById(Long id);
    List<Tenant> findAllByOwner_Id(Long ownerId);
    List<Tenant> findAllByRoom_Id(Long roomId);
}
