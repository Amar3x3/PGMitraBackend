package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface PaymentRepo extends JpaRepository<Payment, Integer> {

}
