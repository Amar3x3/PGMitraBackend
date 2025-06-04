package com.PGmitra.app.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PGmitra.app.Entity.Announcement;

public interface AnnouncementRepo extends JpaRepository<Announcement,Long>{
    List<Announcement> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
} 
