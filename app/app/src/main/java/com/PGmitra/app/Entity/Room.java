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

    private int room_no;
    private int capacity;
    private int occupied; // Should be updated when tenants are added/removed
    private BigDecimal rent; // Added this back as it's usually important

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRoom_no() {
        return room_no;
    }

    public void setRoom_no(int room_no) {
        this.room_no = room_no;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public List<Feedback> getFeedbackAboutRoom() {
        return feedbackAboutRoom;
    }

    public void setFeedbackAboutRoom(List<Feedback> feedbackAboutRoom) {
        this.feedbackAboutRoom = feedbackAboutRoom;
    }

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tenant> tenants = new ArrayList<>();

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", room_no=" + room_no +
                ", capacity=" + capacity +
                ", occupied=" + occupied +
                ", rent=" + rent +
                '}';
    }

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbackAboutRoom = new ArrayList<>();
}