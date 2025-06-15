package com.PGmitra.app.DTO;
import java.time.LocalDate;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DueDateDTO {
    LocalDate dueDate;
}
