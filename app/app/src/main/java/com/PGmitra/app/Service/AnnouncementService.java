package com.PGmitra.app.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PGmitra.app.DTO.AnnouncementDTO;
import com.PGmitra.app.Entity.Announcement;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Repository.AnnouncementRepo;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepo announcementRepo;

    public List<AnnouncementDTO> getAnnouncementsByOwner(Long ownerId) {
        List<Announcement> announcements = announcementRepo.findByOwnerIdOrderByCreatedAtDesc(ownerId);
        if(announcements.isEmpty()) {
            throw new ResourceNotFoundException("No announcements found for Owner with ID:" + ownerId);
        }
        return announcements.stream().map(a -> new AnnouncementDTO(a.getId(), a.getTitle(), a.getText(), a.getCreatedAt())).collect(Collectors.toList());
    }

}
