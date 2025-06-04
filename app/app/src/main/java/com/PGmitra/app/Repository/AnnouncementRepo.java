package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {
}
