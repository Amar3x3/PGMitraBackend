package com.PGmitra.app.Service;

import com.PGmitra.app.DTO.RoomDTO;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Property;
import com.PGmitra.app.Entity.Room;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Exception.RoomCapacityFull;
import com.PGmitra.app.Repository.PropertyRepo;
import com.PGmitra.app.Repository.RoomsRepo;
import com.PGmitra.app.Repository.TenantRepo;
import com.PGmitra.app.Repository.VenderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomsService {
    @Autowired
    private RoomsRepo roomsRepo;
    @Autowired
    private PropertyService propertyService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private PropertyRepo propertyRepo;

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private VenderRepo ownerRepo;

    public Room createRoom(RoomDTO roomDTO){
        Room room = new Room();
        room.setRoom_no(roomDTO.getRoom_no());
        room.setRent(roomDTO.getRent());
        room.setCapacity(roomDTO.getCapacity());
        room.setOccupied(roomDTO.getOccupied());
        room.setProperty(roomDTO.getProperty());

        Optional<Property> property = propertyRepo.findById(roomDTO.getProperty().getId());
//        property.get().getRooms().add(room);

        Room createdRooms = roomsRepo.save(room);
//        propertyRepo.save(property.get());
        return createdRooms;
    }

    public Optional<Room> getRoomByID(long roomId) {
        return roomsRepo.findById(roomId);
    }

    public Room addNewTenant(long roomId, long propertyId, long tenantId) throws RoomCapacityFull {
        Optional<Property> property = propertyService.getPropertyById(propertyId);
        Optional<Room> room = roomsRepo.findById(roomId);
        Optional<Tenant> tenant = tenantService.getTenantById(tenantId);
        Optional<Owner> owner = ownerRepo.findById(property.get().getOwner().getId());

       List<Tenant> tenantList =  room.get().getTenants();
       tenantList.add(tenant.get());

       room.get().setTenants(tenantList);
       if (room.get().getOccupied() < room.get().getCapacity()){
           room.get().setOccupied(room.get().getOccupied() + 1);
       }
       else{
           throw new RoomCapacityFull("Room capacity is Full");
       }
       tenant.get().setRoom(room.get());
       tenant.get().setOwner(owner.get());


       roomsRepo.save(room.get());
       tenantRepo.save(tenant.get());
        return room.get();
    }

    public ResponseEntity<Room> deleteTenant(long roomId, long propertyId, long tenantId) {
        Optional<Property> property = propertyService.getPropertyById(propertyId);
        Optional<Room> room = roomsRepo.findById(roomId);
        Optional<Tenant> tenant = tenantService.getTenantById(tenantId);
//        List<Tenant> tenantList =  room.get().getTenants();
//        tenantList.remove(tenant.get());
//        room.get().setTenants(tenantList);
        room.get().setOccupied(room.get().getOccupied() - 1);
        roomsRepo.save(room.get());
        return new ResponseEntity<>(room.get(), HttpStatus.CREATED);

    }

    public void deleteRoom(Long id) {
        Room room = roomsRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        
        // Check if room has any tenants
        if (room.getOccupied() > 0) {
            throw new IllegalStateException("Cannot delete room with active tenants");
        }
        
        roomsRepo.delete(room);
    }

    public Room updateRoom(Long id, RoomDTO roomDTO) {
        Room room = roomsRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        
        // Validate new capacity is not less than current occupancy
        if (roomDTO.getCapacity() < room.getOccupied()) {
            throw new IllegalStateException("New capacity cannot be less than current occupancy");
        }
        
        room.setCapacity(roomDTO.getCapacity());
        room.setRent(roomDTO.getRent());
        
        return roomsRepo.save(room);
    }
}
