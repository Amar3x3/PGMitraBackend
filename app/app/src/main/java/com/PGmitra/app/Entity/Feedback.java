package com.PGmitra.app.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

// Define this enum separately or in this file
// public enum FeedbackStatus { OPEN, IN_PROGRESS, RESOLVED, CLOSED }

@Entity
@Table(name = "feedback")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Feedback {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String text; // complaint
    private String status;
    private LocalDateTime createdAt;
    private String Title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id") // Feedback can be about a specific room
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id") // Owner to whom it might be visible or addressed
    private Owner owner;
}