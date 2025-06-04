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


}
