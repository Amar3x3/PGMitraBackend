package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Tenant;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepo extends JpaRepository<Tenant, Integer> {
    Optional<Tenant> findByUsername(String username);
    Optional<Tenant> findByEmail(String email);
    Optional<Tenant> findById(Long id);
    List<Tenant> findAllByOwner_Id(Long ownerId);
    List<Tenant> findAllByRoom_Id(Long roomId);

    @Query("SELECT t FROM Tenant t WHERE t.owner.id = :ownerId AND LOWER(t.home_address) LIKE LOWER(CONCAT('%', :state, '%'))")
    List<Tenant> findByHomeAddressContainingAndOwnerId(@Param("state") String state, @Param("ownerId") Long ownerId);

    @Query("SELECT t FROM Tenant t WHERE t.owner.id = :ownerId AND LOWER(t.company_name) LIKE LOWER(CONCAT('%', :company, '%'))")
    List<Tenant> findByCompanyNameContainingAndOwnerId(@Param("company") String company, @Param("ownerId") Long ownerId);
}
