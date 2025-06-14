package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Room;
import com.PGmitra.app.Entity.Property;
// import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomsRepo extends JpaRepository<Room, Long> {

    
    List<Room> findByProperty(Property property);
}
