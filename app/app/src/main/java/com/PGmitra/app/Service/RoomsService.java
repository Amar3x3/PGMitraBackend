package com.PGmitra.app.Service;

import com.PGmitra.app.DTO.RoomDTO;
import com.PGmitra.app.Entity.Room;
import com.PGmitra.app.Repository.RoomsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomsService {
    @Autowired
    private RoomsRepo roomsRepo;
    public Room createRoom(RoomDTO roomDTO){
        Room room = new Room();
        room.setRoom_no(roomDTO.getRoom_no());
        room.setRent(roomDTO.getRent());
        room.setCapacity(roomDTO.getCapacity());
        room.setOccupied(roomDTO.getOccupied());
        room.setProperty(roomDTO.getProperty());

        return roomsRepo.save(room);
    }
}
