package com.PGmitra.app.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tenant_id;
    private String username;

    private String contactNumber;
    private String email;
    private LocalDate leaseStartDate;
    private LocalDate leaseEndDate;

    @ManyToOne
    @Column(name = "vendor_id")
    private Vendor vendor_id;

    @ManyToOne
    @Column(name = "room_id")
    private Rooms room;

}
