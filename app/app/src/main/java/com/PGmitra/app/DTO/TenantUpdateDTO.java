package com.PGmitra.app.DTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TenantUpdateDTO {
    private String name;
    private String phone;
    private String gender;
    private String foodPreference;

    private String emergencyContactName;
    private String emergencyContactPhone;
    private String occupation; 
}
