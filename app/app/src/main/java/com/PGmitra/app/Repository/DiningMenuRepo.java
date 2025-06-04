package com.PGmitra.app.Repository;
import com.PGmitra.app.Entity.DiningMenu;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DiningMenuRepo extends JpaRepository<DiningMenu, Long> {
    Optional<DiningMenu> findByOwnerIdAndDate(Long ownerId, LocalDate date);
}
