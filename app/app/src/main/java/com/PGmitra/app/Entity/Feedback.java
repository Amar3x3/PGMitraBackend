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
    @Column(name = "tenant_id", nullable = false)
    private Tenant tenant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "owner_id", nullable = false)
    private Owner owner;

}