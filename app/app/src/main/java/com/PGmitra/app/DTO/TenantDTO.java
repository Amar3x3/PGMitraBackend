package com.PGmitra.app.DTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TenantDTO {

    private String name;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String aadharNumber; 
    private String occupation;
}
