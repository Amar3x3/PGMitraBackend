package com.PGmitra.app.DTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TenantDTO {

    private String name;
    private String userName;
    private String phoneNumber;
    private String email;
    private String gender;
    private String foodPreference;
    private String password;

}

