package com.PGmitra.app.Service;

import com.PGmitra.app.Entity.Vendor;
import com.PGmitra.app.Repository.VenderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class VendorService {
    @Autowired
    private VenderRepo vendorRepository;

    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }
}
