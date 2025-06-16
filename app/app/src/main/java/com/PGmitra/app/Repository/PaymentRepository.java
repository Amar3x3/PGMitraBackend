package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Payment;
import com.PGmitra.app.Enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStatus(Status status);
} 