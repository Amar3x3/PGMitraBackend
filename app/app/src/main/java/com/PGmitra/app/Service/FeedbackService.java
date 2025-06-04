package com.PGmitra.app.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PGmitra.app.DTO.FeedbackDTO;
import com.PGmitra.app.Entity.Feedback;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Room;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Repository.FeedbackRepo;
import com.PGmitra.app.Repository.TenantRepo;

@Service
public class FeedbackService {

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private FeedbackRepo feedbackRepo;
    
    public void submitFeedback(FeedbackDTO dto, Long tenantId) {
        Tenant tenant =  tenantRepo.findById(tenantId).orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        Room room = tenant.getRoom();
        if (room == null) {
            throw new ResourceNotFoundException("Room not assigned to tenant");
        }
        Owner owner = tenant.getOwner();
        if (owner == null) {
            throw new ResourceNotFoundException("Owner not found for tenant's room.");
        }

        Feedback feedback = new Feedback();

        feedback.setTitle(dto.getTitle());
        feedback.setText(dto.getText());
        feedback.setStatus("Pending");
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setTenant(tenant);
        feedback.setOwner(owner);
        feedback.setRoom(room);

        feedbackRepo.save(feedback);

    }
}
