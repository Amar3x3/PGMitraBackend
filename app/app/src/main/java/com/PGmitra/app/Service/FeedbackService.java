package com.PGmitra.app.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PGmitra.app.DTO.FeedbackDTO;
import com.PGmitra.app.DTO.FeedbackViewDTO;
import com.PGmitra.app.Entity.Feedback;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Room;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Repository.FeedbackRepo;
import com.PGmitra.app.Repository.TenantRepo;
import com.PGmitra.app.Repository.VenderRepo;

@Service
public class FeedbackService {

    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private FeedbackRepo feedbackRepo;

    @Autowired
    private VenderRepo ownerRepo;
    
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

    private FeedbackViewDTO mapToComplaintViewDto(Feedback feedback) {
        FeedbackViewDTO dto = new FeedbackViewDTO();
        dto.setId(feedback.getId());
        dto.setTitle(feedback.getTitle());
        dto.setStatus(feedback.getStatus());
        dto.setText(feedback.getText());
        if (feedback.getRoom() != null) {
            dto.setRoomNumber(feedback.getRoom().getRoom_no());
        }
        dto.setCreatedAt(feedback.getCreatedAt());
        return dto;
    }

    public List<FeedbackViewDTO> getAllFeedbackByOwner(Long ownerId) {
        Owner owner = ownerRepo.findById(ownerId).orElseThrow(() -> new ResourceNotFoundException("Owner not found with ID " + ownerId));

        List<Feedback> feedbackList = feedbackRepo.findByOwner(owner);

        if (feedbackList.isEmpty()) {
            throw new ResourceNotFoundException("No complaints found for owner with ID " + ownerId);
        }

        return feedbackList.stream().map(this::mapToComplaintViewDto).collect(Collectors.toList());

    }

    public void markAsComplete(Long complaintId) {
        Feedback feedback = feedbackRepo.findById(complaintId).orElseThrow(() -> new ResourceNotFoundException("Feedback not found with Id: " + complaintId));
        feedback.setStatus("Complete");
        feedbackRepo.save(feedback);
    }

}
