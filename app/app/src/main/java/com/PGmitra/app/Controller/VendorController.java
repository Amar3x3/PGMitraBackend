package com.PGmitra.app.Controller;

import com.PGmitra.app.DTO.FeedbackDTO;
import com.PGmitra.app.DTO.FeedbackViewDTO;
import com.PGmitra.app.DTO.FeedbackDTO;
import com.PGmitra.app.DTO.FeedbackViewDTO;
import com.PGmitra.app.DTO.OwnerDTO;
import com.PGmitra.app.DTO.PaymentDTO;
import com.PGmitra.app.DTO.PropertyDTO;
import com.PGmitra.app.DTO.RoomDTO;
import com.PGmitra.app.Entity.*;
import com.PGmitra.app.Enums.Status;
import com.PGmitra.app.Exception.ResourceAlreadyExistsException;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Exception.RoomCapacityFull;
import com.PGmitra.app.Response.*;
import com.PGmitra.app.Service.PaymentService;
import com.PGmitra.app.Service.FeedbackService;
import com.PGmitra.app.Service.PropertyService;
import com.PGmitra.app.Service.RoomsService;
import com.PGmitra.app.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private RoomsService roomsService;

    @Autowired
    private PaymentService paymentService;

    @Autowired 
    private FeedbackService feedbackService;

    @GetMapping("/hello")
    public String hello(){
        return "hello from vendor api";
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createNewVendor(@RequestBody OwnerDTO ownerDTO) {
        try {
            Owner createdVendor = vendorService.createVendor(ownerDTO);
            return new ResponseEntity<>(createdVendor, HttpStatus.CREATED);
        } catch (ResourceAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new StatusAndMessageResponse(HttpStatus.CONFLICT, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    // @PostMapping("/login")
    // public ResponseEntity<Object> loginOwner(@RequestBody LoginRequest loginRequest) {
    //     try {
    //         boolean login = vendorService.loginOwner(loginRequest.username(), loginRequest.password());
    //         if (login) {
    //             return ResponseEntity.ok(new LoginResponse(loginRequest.username(), "Login successful", HttpStatus.OK));
    //         }
    //         return ResponseEntity.status(HttpStatus.FORBIDDEN)
    //             .body(new LoginResponse(loginRequest.username(), "Invalid credentials", HttpStatus.FORBIDDEN));
    //     } catch (Exception ex) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //             .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
    //     }
    // }

    @PostMapping("/property/{id}")
    public ResponseEntity<Object> createNewProperty(@RequestBody PropertyDTO request, @PathVariable Long id) {
        try {
            Property createdProperty = propertyService.createNewProperty(request, id);
            return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }
    

    @PostMapping("/room/{id}")
    public ResponseEntity<Object> createNewRoom(@RequestBody RoomDTO roomDTO, @PathVariable Long id) {
        try {
            Optional<Property> property = propertyService.getPropertyById(id);
            if (property.isEmpty()) {
                throw new ResourceNotFoundException("Property not found with id: " + id);
            }
            roomDTO.setProperty(property.get());
            Room createdRoom = roomsService.createRoom(roomDTO);
            return new ResponseEntity<>(new StatusAndMessageResponse(HttpStatus.OK, createdRoom.toString()), HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }
    
    @PostMapping("/addNewTenant")
    public ResponseEntity<Object> addNewMember(@RequestBody RoomMemberRequest roomMemberRequest) {
        try {
            long room_id = roomMemberRequest.room_id();
            long property_id = roomMemberRequest.property_id();
            long tenant_id = roomMemberRequest.tenant_id();

            Room createdRoom = roomsService.addNewTenant(room_id, property_id, tenant_id);
            return new ResponseEntity<>(new StatusAndMessageResponse(HttpStatus.OK, createdRoom.toString()), HttpStatus.CREATED);
        } catch (RoomCapacityFull ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new StatusAndMessageResponse(HttpStatus.CONFLICT, ex.getMessage()));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }
            

    @PostMapping("/deleteTenant")
    public ResponseEntity<Object> deleteTenant(@RequestBody RoomMemberRequest roomMemberRequest) {
        try {
            long room_id = roomMemberRequest.room_id();
            long property_id = roomMemberRequest.property_id();
            long tenant_id = roomMemberRequest.tenant_id();
            ResponseEntity<Room> response = roomsService.deleteTenant(room_id, property_id, tenant_id);
            return ResponseEntity.ok(response.getBody());
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    @PostMapping("/announcement")
    public ResponseEntity<Object> addNewAnnouncement(@RequestBody AnnouncementRequest announcementRequest) {
        try {
            Announcement announcement = vendorService.createNewAnnouncement(announcementRequest);
            return new ResponseEntity<>(new StatusAndMessageResponse(HttpStatus.OK, announcement.toString()), HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<Object> createPayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            Payment payment = paymentService.createPayment(paymentDTO);
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    @GetMapping("/payments/{ownerId}")
    public ResponseEntity<Object> getPaymentsByOwner(@PathVariable Long ownerId) {
        try {
            List<Payment> payments = paymentService.getPaymentsByOwner(ownerId);
            return ResponseEntity.ok(payments);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    @PutMapping("/payment/{paymentId}/status")
    public ResponseEntity<Object> updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestParam Status status) {
        try {
            Payment payment = paymentService.updatePaymentStatus(paymentId, status);
            return ResponseEntity.ok(payment);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    @GetMapping("/complaints/{ownerId}")
    public ResponseEntity<Object> getComplaints(@PathVariable Long ownerId) {
        try {
            List<FeedbackViewDTO> feedbackList = feedbackService.getAllFeedbackByOwner(ownerId);
            return new ResponseEntity<>(feedbackList, HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    }

    @PutMapping("/complaints/{id}/complete")
    public ResponseEntity<Object> markComplaintAsComplete(@PathVariable Long id) {
        try{
            feedbackService.markAsComplete(id);
            return ResponseEntity.ok("Complaint marked as fixed");
        } catch(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    
}
