package com.PGmitra.app.Entity;



import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate; // Or DayOfWeek for weekly recurring
import java.time.LocalDateTime;

@Entity
@Table(name = "dining_menus")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiningMenu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private LocalDate date;
    @Lob private String text; // menu items for the day
    // Consider separate fields for breakfast, lunch, dinner if needed
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false) // Or property_id if menu is per property
    private Owner owner;
}