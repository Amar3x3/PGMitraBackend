package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomsRepo extends JpaRepository<Room, Long> {
}
