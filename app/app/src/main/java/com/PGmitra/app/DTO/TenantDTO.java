package com.PGmitra.app.DTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TenantDTO {

    private String name;
    private String password;
    private String username;
    private String phonenumber;
    private String email;
    private String gender;

    
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String aadharNumber; 
    private String occupation;
    private String home_address;
    private String company_name;
}

