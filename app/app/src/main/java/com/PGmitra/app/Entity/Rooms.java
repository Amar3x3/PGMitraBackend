package com.PGmitra.app.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer room_id;

    @ManyToOne
    @Column(name = "vendor_id")
    private Vendor vendor_id;

    private Integer capacity;
    private Integer occupied;

    private Integer rent;

    @OneToMany
    private List<Tenant> tenantList;


}
