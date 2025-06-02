package com.PGmitra.app.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
