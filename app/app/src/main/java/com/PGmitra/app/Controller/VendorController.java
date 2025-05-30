package com.PGmitra.app.Controller;

import com.PGmitra.app.Entity.Vendor;
import com.PGmitra.app.Response.StatusAndMessageResponse;
import com.PGmitra.app.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {


    @Autowired
    private VendorService vendorService;

    @GetMapping("/hello")
    public String hello(){
        return "hello from vendor api";
    }

    @PostMapping("/register")
    public ResponseEntity<Vendor> createNewVendor(@RequestBody Vendor newVendor) {
        Vendor createdVendor = vendorService.createVendor(newVendor);
        return new ResponseEntity<>(createdVendor, HttpStatus.CREATED);
    }
    
}
