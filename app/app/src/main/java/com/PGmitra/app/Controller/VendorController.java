package com.PGmitra.app.Controller;

import com.PGmitra.app.DTO.OwnerDTO;
import com.PGmitra.app.DTO.RoomDTO;
import com.PGmitra.app.Entity.*;
import com.PGmitra.app.Exception.ResourceAlreadyExistsException;
import com.PGmitra.app.Response.*;
import com.PGmitra.app.Service.PropertyService;
import com.PGmitra.app.Service.RoomsService;
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

    @Autowired
    private RoomsService roomsService;


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

    @PostMapping("/room/{id}")
    public ResponseEntity<StatusAndMessageResponse> createNewRoom(@RequestBody RoomDTO roomDTO, @PathVariable Long id){
        Optional<Property> property = propertyService.getPropertyById(id);
        roomDTO.setProperty(property.get());
        Room createdRoom =  roomsService.createRoom(roomDTO);
        return new ResponseEntity<>(new StatusAndMessageResponse(HttpStatus.OK,createdRoom.toString()) ,HttpStatus.CREATED);
    }

    @PostMapping("/addNewTenant")
    public ResponseEntity<Room> addNewMember(@RequestBody RoomMemberRequest roomMemberRequest){
        long room_id = roomMemberRequest.room_id();
        long property_id = roomMemberRequest.property_id();
        long tenant_id = roomMemberRequest.tenant_id();
        return roomsService.addNewTenant(room_id, property_id, tenant_id);
    }
    @PostMapping("/deleteTenant")
    public ResponseEntity<Room> deleteTenant(@RequestBody RoomMemberRequest roomMemberRequest){
        long room_id = roomMemberRequest.room_id();
        long property_id = roomMemberRequest.property_id();
        long tenant_id = roomMemberRequest.tenant_id();
        return roomsService.deleteTenant(room_id, property_id, tenant_id);
    }

    @PostMapping("/announcement")
    public ResponseEntity<StatusAndMessageResponse> addNewAnnouncement(@RequestBody AnnouncementRequest announcementRequest){
        Announcement announcement = vendorService.createNewAnnouncement(announcementRequest);
        return new ResponseEntity<>(new StatusAndMessageResponse(HttpStatus.OK, announcement.toString()), HttpStatus.CREATED);
    }





}
