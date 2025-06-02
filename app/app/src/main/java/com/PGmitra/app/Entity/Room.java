package com.PGmitra.app.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private int capacity;
    private int occupied; // Should be updated when tenants are added/removed
    private BigDecimal rent; // Added this back as it's usually important

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tenant> tenants = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbackAboutRoom = new ArrayList<>();
}