package com.PGmitra.app.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PGmitra.app.DTO.AnnouncementDTO;
import com.PGmitra.app.Entity.Announcement;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Repository.AnnouncementRepo;
import com.PGmitra.app.Repository.TenantRepo;
import com.PGmitra.app.Entity.Tenant;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepo announcementRepo;

    @Autowired
    private TenantRepo tenantRepo;

    public List<AnnouncementDTO> getAnnouncementsByOwner(Long ownerId) {
        List<Announcement> announcements = announcementRepo.findByOwnerIdOrderByCreatedAtDesc(ownerId);
        if(announcements.isEmpty()) {
            throw new ResourceNotFoundException("No announcements found for Owner with ID:" + ownerId);
        }
        return announcements.stream().map(a -> new AnnouncementDTO(a.getId(), a.getTitle(), a.getText(), a.getCreatedAt())).collect(Collectors.toList());
    }

    public List<AnnouncementDTO> getAnnouncementsByTenant(Long tenantId) {
        Tenant tenant = tenantRepo.findById(tenantId).orElseThrow(() -> new ResourceNotFoundException("Tenant not found with ID:" + tenantId));
        Owner owner = null;
        if (tenant.getOwner() != null) {
            owner = tenant.getOwner();
        } else {
            throw new ResourceNotFoundException("No owner found for tenant with ID:" + tenantId);
        }
        List<Announcement> announcements = announcementRepo.findByOwnerIdOrderByCreatedAtDesc(owner.getId());
        if(announcements.isEmpty()) {
            throw new ResourceNotFoundException("No announcements found for Owner with ID:" + owner.getId());
        }
        return announcements.stream().map(a -> new AnnouncementDTO(a.getId(), a.getTitle(), a.getText(), a.getCreatedAt())).collect(Collectors.toList());
    }
}
