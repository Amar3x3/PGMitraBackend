package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Payment;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findByTenant(Tenant tenant);
    List<Payment> findByOwner(Owner owner);
    List<Payment> findByStatusAndDueDateBefore(Status status, LocalDate date);
    List<Payment> findByStatus(Status status);
}
