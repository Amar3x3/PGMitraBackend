package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepo extends JpaRepository<Tenant, Long> {
}
