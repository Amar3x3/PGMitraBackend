package com.PGmitra.app.DTO;
import com.PGmitra.app.Entity.Property;

import java.math.BigDecimal;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private int room_no;
    private int capacity;
    private int occupied; // Should be updated when tenants are added/removed
    private BigDecimal rent; // Added this back as it's usually important
    private Property property;

    @Override
    public String toString() {
        return "RoomDTO{" +
                "room_no=" + room_no +
                ", capacity=" + capacity +
                ", occupied=" + occupied +
                ", rent=" + rent +
                ", property=" + property.getId() +
                '}';
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
}
