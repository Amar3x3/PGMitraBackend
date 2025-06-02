package com.PGmitra.app.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tenants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tenant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    @Column(unique = true) private String username;
    private String password;
    private String phone;
    @Column(unique = true) private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbackSubmitted = new ArrayList<>();

    // Add other fields like moveInDate, permanentAddress etc. as per full feature list.
}