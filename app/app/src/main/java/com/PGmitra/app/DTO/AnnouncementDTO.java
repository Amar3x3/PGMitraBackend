package com.PGmitra.app.DTO;

import java.time.LocalDateTime;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDTO {
    private Long id;
    private String title;
    private String text;
    private LocalDateTime createdAt;

}
