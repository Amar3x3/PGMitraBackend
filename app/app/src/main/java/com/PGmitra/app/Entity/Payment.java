package com.PGmitra.app.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Define this enum separately or in this file if small
// public enum PaymentStatus { PAID, PENDING, OVERDUE, PARTIALLY_PAID }

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private BigDecimal amount; // CRITICAL: Added amount
    private String status;
    private LocalDate date; // Consider LocalDateTime if time is important
    private String paymentMethod;
    private int paymentForMonth; // e.g., 1 for Jan, 12 for Dec
    private int paymentForYear;  // e.g., 2024


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;
}