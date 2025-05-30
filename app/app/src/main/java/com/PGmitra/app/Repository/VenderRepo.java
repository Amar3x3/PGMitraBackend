package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenderRepo extends JpaRepository<Vendor, Integer> {
}
