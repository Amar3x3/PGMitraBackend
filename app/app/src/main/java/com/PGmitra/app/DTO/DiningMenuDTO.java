package com.PGmitra.app.DTO;

import java.time.LocalDate;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DiningMenuDTO {
    private Long id;
    private LocalDate date;
    private String Breakfast;
    private String Lunch;
    private String Dinner;

    Long ownerId;

}
