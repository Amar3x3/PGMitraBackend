package com.PGmitra.app.DTO;

import com.PGmitra.app.Entity.Owner;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {
    private Long prop_id;
    private String name;
    private String address;

    public void setPropId(Long prop_id){
        this.prop_id = prop_id;
    }

    public Long getPropId() {
        return prop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
