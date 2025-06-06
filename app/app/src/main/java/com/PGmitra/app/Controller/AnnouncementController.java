package com.PGmitra.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PGmitra.app.DTO.AnnouncementDTO;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Service.AnnouncementService;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {
    @Autowired AnnouncementService announcementService;

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<Object> getAnnouncementsByOwner(@PathVariable Long ownerId) {
        try{
            List<AnnouncementDTO> announcements = announcementService.getAnnouncementsByOwner(ownerId);
            return new ResponseEntity<>(announcements, HttpStatus.OK);
        }
        catch(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        
    }

}
