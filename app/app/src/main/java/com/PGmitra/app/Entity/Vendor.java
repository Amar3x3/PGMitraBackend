package com.PGmitra.app.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendors") // Plural table name is a common convention
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder // Useful for creating instances
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer vendor_id;
    String username;
    String email;
    String password;

    private String emailAppPassword;

    @OneToMany
    List<Tenant> tenants;

    @OneToMany
    List<Rooms> rooms;

}
