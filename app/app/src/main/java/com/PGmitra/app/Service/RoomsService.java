package com.PGmitra.app.Service;

import com.PGmitra.app.DTO.RoomDTO;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Property;
import com.PGmitra.app.Entity.Room;
import com.PGmitra.app.Entity.Tenant;
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

    public ResponseEntity<Room> addNewTenant(long roomId, long propertyId, long tenantId) {
        Optional<Property> property = propertyService.getPropertyById(propertyId);
        Optional<Room> room = roomsRepo.findById(roomId);
        Optional<Tenant> tenant = tenantService.getTenantById(tenantId);
        Optional<Owner> owner = ownerRepo.findById(property.get().getOwner().getId());

       List<Tenant> tenantList =  room.get().getTenants();
       tenantList.add(tenant.get());

       room.get().setTenants(tenantList);
       tenant.get().setRoom(room.get());
       tenant.get().setOwner(owner.get());

       tenantRepo.save(tenant.get());
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
