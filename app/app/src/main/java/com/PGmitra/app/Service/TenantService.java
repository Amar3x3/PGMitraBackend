package com.PGmitra.app.Service;

import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Repository.TenantRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TenantService {
    @Autowired
    private TenantRepo tenantRepo;

    public Optional<Tenant> getTenantById(long id){
        return tenantRepo.findById(id);
    }
}
