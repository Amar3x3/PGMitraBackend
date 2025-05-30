package com.PGmitra.app.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paymentId;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Rooms room;

    private LocalDate paymentDate;
    private LocalDate paymentForMonth; // Represents the month for which the payment is made
    private Integer amountPaid; // Assuming rent is in whole numbers

    private String paymentMethod;

    private String paymentStatus;
}