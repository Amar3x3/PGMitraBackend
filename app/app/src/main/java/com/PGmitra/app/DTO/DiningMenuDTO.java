package com.PGmitra.app.DTO;

import java.time.LocalDate;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DiningMenuDTO {
    private LocalDate date;
    private String text;
}
