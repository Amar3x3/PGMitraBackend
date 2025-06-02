package com.PGmitra.app.DTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    private String roomNumber;
    private String roomType;
    private double rent;
    private boolean occupied;
    private String occupantName; 
    private String occupantPhone; 
}
