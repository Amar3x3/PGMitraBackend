package com.PGmitra.app.Service;

import com.PGmitra.app.DTO.RoomDTO;
import com.PGmitra.app.Entity.Property;
import com.PGmitra.app.Entity.Room;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Repository.RoomsRepo;
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

    public Room createRoom(RoomDTO roomDTO){
        Room room = new Room();
        room.setRoom_no(roomDTO.getRoom_no());
        room.setRent(roomDTO.getRent());
        room.setCapacity(roomDTO.getCapacity());
        room.setOccupied(roomDTO.getOccupied());
        room.setProperty(roomDTO.getProperty());

        return roomsRepo.save(room);
    }

    public Optional<Room> getRoomByID(long roomId) {
        return roomsRepo.findById(roomId);
    }

    public ResponseEntity<Room> addNewTenant(long roomId, long propertyId, long tenantId) {
        Optional<Property> property = propertyService.getPropertyById(propertyId);
        Optional<Room> room = roomsRepo.findById(roomId);
        Optional<Tenant> tenant = tenantService.getTenantById(tenantId);
       List<Tenant> tenantList =  room.get().getTenants();
       tenantList.add(tenant.get());
       room.get().setTenants(tenantList);
       roomsRepo.save(room.get());
        return new ResponseEntity<>(room.get(), HttpStatus.CREATED);
    }

    public ResponseEntity<Room> deleteTenant(long roomId, long propertyId, long tenantId) {
        Optional<Property> property = propertyService.getPropertyById(propertyId);
        Optional<Room> room = roomsRepo.findById(roomId);
        Optional<Tenant> tenant = tenantService.getTenantById(tenantId);
        List<Tenant> tenantList =  room.get().getTenants();
        tenantList.remove(tenant.get());
        room.get().setTenants(tenantList);
        roomsRepo.save(room.get());
        return new ResponseEntity<>(room.get(), HttpStatus.CREATED);

    }
}
