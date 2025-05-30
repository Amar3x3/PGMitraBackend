package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomsRepo extends JpaRepository<Rooms, Integer> {
}
