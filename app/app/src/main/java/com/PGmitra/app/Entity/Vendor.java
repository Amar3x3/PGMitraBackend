package com.PGmitra.app.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendors") // Plural table name is a common convention
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder // Useful for creating instances
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer vendor_id;
    String username;
    String email;
    String password;

    public Integer getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(Integer vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAppPassword() {
        return emailAppPassword;
    }

    public void setEmailAppPassword(String emailAppPassword) {
        this.emailAppPassword = emailAppPassword;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    private String emailAppPassword;

    @OneToMany
    List<Tenant> tenants;

    @OneToMany
    List<Room> rooms;

}
