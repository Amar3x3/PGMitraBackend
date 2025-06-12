package com.PGmitra.app.DTO;

import java.time.LocalDateTime;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FeedbackViewDTO {
    private Long id;
    private String title;
    private String text;
    private String status;
    private int roomNumber;
    private LocalDateTime createdAt;    
}
