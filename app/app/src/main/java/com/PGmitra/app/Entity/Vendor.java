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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY is often preferred for auto-increment PKs
    @Column(name = "vendor_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

//
//    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Property> properties = new ArrayList<>();
//
//
//    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Announcement> announcements = new ArrayList<>();
//
//
//    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<DiningMenu> diningMenus = new ArrayList<>();
//
//
//    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
//    private List<Tenant> tenants = new ArrayList<>();
}