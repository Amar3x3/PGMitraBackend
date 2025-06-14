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
import com.PGmitra.app.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;



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

    @Autowired
    private TenantService tenantService;

    @Autowired
    private EmailService emailService;


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

    @GetMapping("/allTenants/{owner_id}")
    public ResponseEntity<Object> getAllTenants(@PathVariable Long owner_id){
        List<Tenant> tenants = tenantService.getAllTenants(owner_id);
        List<TenantResponse> tenantResponseList = new ArrayList<>();
        if (tenants.isEmpty()) System.out.println("tenats empty");
        for (Tenant it : tenants){
            TenantResponse tenantResponse = new TenantResponse(it.getId(), it.getName(), it.getRoom().getRoom_no(), it.getPhone());
            System.out.println(tenantResponse.toString());
            tenantResponseList.add(tenantResponse);
        }
        return new ResponseEntity<>(tenantResponseList, HttpStatus.OK);
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
            PropertyResponse  propertyResponse= new PropertyResponse(createdProperty.getId(), createdProperty.getName(), createdProperty.getAddress());

            return new ResponseEntity<>(propertyResponse, HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    @GetMapping("/property/{id}")
    public ResponseEntity<Object> getAllProperty(@PathVariable Long id) {
        try {
           List<Property> properties = propertyService.getAllProperty(id);
           List<PropertyDTO> propertyDTOS = new ArrayList<>();
           for (Property property : properties){
               PropertyDTO propertyDTO = new PropertyDTO(property.getId(), property.getName(), property.getAddress());
               propertyDTOS.add(propertyDTO);
           }
            
            return new ResponseEntity<>(propertyDTOS, HttpStatus.CREATED);
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
            RoomResponse roomResponse = new RoomResponse(createdRoom.getRoom_no(), createdRoom.getId(), createdRoom.getCapacity(), createdRoom.getOccupied(), createdRoom.getRent());
            return new ResponseEntity<>(roomResponse, HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<Object> getAllRoomsByPropertyId(@PathVariable Long id) {
        try {
            Optional<Property> property = propertyService.getPropertyById(id);
            if (property.isEmpty()) {
                throw new ResourceNotFoundException("Property not found with id: " + id);
            }
            List<Room> rooms = roomsService.getRoomsByProperty(property.get());
            List<RoomResponse> roomResponses = rooms.stream()
                .map(room -> new RoomResponse(room.getRoom_no(), room.getId(), room.getCapacity(), room.getOccupied(), room.getRent()))
                .collect(Collectors.toList());
            
                return new ResponseEntity<>(roomResponses, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }
    
    // Amar's GetMapping Snippet
    // @GetMapping("/room/{id}")
    // public ResponseEntity<Object> getAllRooms(@RequestBody RoomDTO roomDTO, @PathVariable Long id) {
    //     try {
    //         Optional<Property> property = propertyService.getPropertyById(id);
    //         if (property.isEmpty()) {
    //             throw new ResourceNotFoundException("Property not found with id: " + id);
    //         }
    //         roomDTO.setProperty(property.get());
    //         Room createdRoom = roomsService.createRoom(roomDTO);
    //         RoomResponse roomResponse = new RoomResponse(createdRoom.getRoom_no(), createdRoom.getId(), createdRoom.getCapacity(), createdRoom.getOccupied(), createdRoom.getRent());
    //         return new ResponseEntity<>(roomResponse, HttpStatus.CREATED);
    //     } catch (ResourceNotFoundException ex) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //                 .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
    //     } catch (Exception ex) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
    //     }
    // }

    @PostMapping("/addNewTenant")
    public ResponseEntity<Object> addNewMember(@RequestBody RoomMemberRequest roomMemberRequest) {
        try {
            long room_id = roomMemberRequest.room_id();
            long property_id = roomMemberRequest.property_id();
            long tenant_id = roomMemberRequest.tenant_id();

            Room createdRoom = roomsService.addNewTenant(room_id, property_id, tenant_id);
            RoomResponse roomResponse = new RoomResponse(createdRoom.getRoom_no(), createdRoom.getId(), createdRoom.getOccupied(), createdRoom.getCapacity(), createdRoom.getRent());
            return new ResponseEntity<>(roomResponse, HttpStatus.CREATED);
        } catch (RoomCapacityFull ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new StatusAndMessageResponse(HttpStatus.CONFLICT, ex.getMessage()));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred" + ex.getMessage()));
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
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
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
            PaymentResponse paymentResponse = new PaymentResponse(payment.getId(), payment.getTenant().getName(), payment.getAmount(), payment.getStatus(), payment.getDueDate());
            return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
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
            List<PaymentResponse> paymentResponses = new ArrayList<>();
            for (Payment it : payments){
                PaymentResponse paymentResponse = new PaymentResponse(it.getId(), it.getTenant().getName(), it.getAmount(), it.getStatus(), it.getDueDate());
                paymentResponses.add(paymentResponse);
            }
            return ResponseEntity.ok(paymentResponses);
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

    @PostMapping("/send-reminder/{tenantId}")
    public ResponseEntity<Object> sendRentReminder(@PathVariable Long tenantId) {
        try {
            
            Tenant tenant = tenantService.getTenantById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

          
            List<Payment> payments = paymentService.getPaymentsByTenant(tenantId);
            Payment latestPayment = payments.stream()
                .filter(p -> p.getStatus() == Status.INCOMPLETE)
                .max((p1, p2) -> p1.getDueDate().compareTo(p2.getDueDate()))
                .orElse(null);

            if (latestPayment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, "No pending payments found for this tenant"));
            }

            int daysUntilDue = (int) java.time.temporal.ChronoUnit.DAYS.between(
                LocalDate.now(), 
                latestPayment.getDueDate()
            );

           
            emailService.sendPaymentReminder(tenant, latestPayment, daysUntilDue);

            return ResponseEntity.ok(new StatusAndMessageResponse(HttpStatus.OK, "Rent reminder sent successfully"));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
        }
    }

    @DeleteMapping("/property/{propertyId}")
    public ResponseEntity<Object> deleteProperty(@PathVariable Long propertyId) {
        try {
            propertyService.deleteProperty(propertyId);
            return ResponseEntity.ok(new StatusAndMessageResponse(HttpStatus.OK, "Property deleted successfully"));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    @PutMapping("/property/{propertyId}")
    public ResponseEntity<Object> updateProperty(
            @PathVariable Long propertyId,
            @RequestBody PropertyDTO propertyDTO) {
        try {
            Property updatedProperty = propertyService.updateProperty(propertyId, propertyDTO);
            PropertyResponse propertyResponse = new PropertyResponse(
                updatedProperty.getId(),
                updatedProperty.getName(),
                updatedProperty.getAddress()
            );
            return ResponseEntity.ok(propertyResponse);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }


    //added getMapping for Room Id
    @GetMapping("/roomdetails/{roomId}") 
    public ResponseEntity<Object> getRoomDetailsById(@PathVariable Long roomId) {
        try {
           
            Optional<Room> roomOptional = roomsService.getRoomByID(roomId);
            if (roomOptional.isEmpty()) {
                throw new ResourceNotFoundException("Room not found with id: " + roomId);
            }
            Room foundRoom = roomOptional.get();
            RoomResponse roomResponse = new RoomResponse(
                foundRoom.getRoom_no(),
                foundRoom.getId(),
                foundRoom.getCapacity(),
                foundRoom.getOccupied(),
                foundRoom.getRent()
            );

            return new ResponseEntity<>(roomResponse, HttpStatus.OK);

        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred while fetching room details"));
        }
    }

    @DeleteMapping("/room/{roomId}")
    public ResponseEntity<Object> deleteRoom(@PathVariable Long roomId) {
        try {
            roomsService.deleteRoom(roomId);
            return ResponseEntity.ok(new StatusAndMessageResponse(HttpStatus.OK, "Room deleted successfully"));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    @PutMapping("/room/{roomId}")
    public ResponseEntity<Object> updateRoom(
            @PathVariable Long roomId,
            @RequestBody RoomDTO roomDTO) {
        try {
            Room updatedRoom = roomsService.updateRoom(roomId, roomDTO);
            RoomResponse roomResponse = new RoomResponse(
                updatedRoom.getRoom_no(),
                updatedRoom.getId(),
                updatedRoom.getCapacity(),
                updatedRoom.getOccupied(),
                updatedRoom.getRent()
            );
            return ResponseEntity.ok(roomResponse);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }

    @GetMapping("/tenants/{roomId}")
    public ResponseEntity<Object> getTenantsByRoom(@PathVariable Long roomId) {
        try {
            List<Tenant> tenants = tenantService.getTenantsByRoom(roomId);
            List<TenantResponse> tenantResponseList = new ArrayList<>();
            
            for (Tenant tenant : tenants) {
                TenantResponse tenantResponse = new TenantResponse(
                    tenant.getId(),
                    tenant.getName(),
                    tenant.getRoom().getRoom_no(),
                    tenant.getPhone()
                );
                tenantResponseList.add(tenantResponse);
            }
            
            return new ResponseEntity<>(tenantResponseList, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
        }
    }
    
    @GetMapping("/roomdetails/{roomId}") 
    public ResponseEntity<Object> getRoomDetailsById(@PathVariable Long roomId) {
        try {
            Optional<Room> roomOptional = roomsService.getRoomByID(roomId);
            if (roomOptional.isEmpty()) {
                throw new ResourceNotFoundException("Room not found with id: " + roomId);
            }
            Room foundRoom = roomOptional.get();
            RoomResponse roomResponse = new RoomResponse(
                foundRoom.getRoom_no(),
                foundRoom.getId(),
                foundRoom.getCapacity(),
                foundRoom.getOccupied(),
                fondRoom.getRent()
            );
            return new ResponseEntity<>(roomResponse, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusAndMessageResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusAndMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred while fetching room details"));

        }


    }
    
}
