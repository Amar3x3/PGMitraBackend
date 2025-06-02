package com.PGmitra.app.Controller;

import com.PGmitra.app.DTO.OwnerDTO;
import com.PGmitra.app.DTO.RoomDTO;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Property;
import com.PGmitra.app.Entity.Room;
import com.PGmitra.app.Entity.Vendor;
import com.PGmitra.app.Exception.ResourceAlreadyExistsException;
import com.PGmitra.app.Response.LoginRequest;
import com.PGmitra.app.Response.LoginResponse;
import com.PGmitra.app.Response.PropertyRequest;
import com.PGmitra.app.Response.StatusAndMessageResponse;
import com.PGmitra.app.Service.PropertyService;
import com.PGmitra.app.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {


    @Autowired
    private VendorService vendorService;

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/hello")
    public String hello(){
        return "hello from vendor api";
    }

    @PostMapping("/register")
    public ResponseEntity<Owner> createNewVendor(@RequestBody OwnerDTO ownerDTO) throws ResourceAlreadyExistsException {
        Owner createdVendor = vendorService.createVendor(ownerDTO);
        return new ResponseEntity<>(createdVendor, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public LoginResponse loginOwner(@RequestBody LoginRequest loginRequest){
        boolean login = vendorService.loginOwner(loginRequest.username(), loginRequest.password());
        if(login)
            return new LoginResponse(loginRequest.username(), "login succesful", HttpStatus.OK);
        else  return new LoginResponse(loginRequest.username(), "no user found", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/property")
    public ResponseEntity<Property> createNewProperty(@RequestBody Property request){

        Property createdProperty = propertyService.createNewProperty(request);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }

//    @PostMapping("/room/{id}")
//    public ResponseEntity<Room> createNewRoom(@RequestBody RoomDTO roomDTO, @PathVariable Long id){
//        Optional<Property> property = propertyService.getPropertyById(id);
//
//    }

}
