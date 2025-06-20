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
    private String name;
    private String phone;
    private String gender;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String occupation; 
    private Integer roomNo;
    private String aadharNumber;
    private String home_address;
    private String company_name;
}