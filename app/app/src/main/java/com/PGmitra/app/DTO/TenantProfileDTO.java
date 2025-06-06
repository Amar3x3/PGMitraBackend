package com.PGmitra.app.DTO;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TenantProfileDTO {
    private Long id;
    private String email;
    private String phone;
    private String gender;
}