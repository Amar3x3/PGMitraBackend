package com.PGmitra.app.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PGmitra.app.DTO.DiningMenuDTO;
import com.PGmitra.app.Entity.DiningMenu;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Repository.DiningMenuRepo;
import com.PGmitra.app.Repository.VenderRepo;

import jakarta.transaction.Transactional;

@Service
public class DiningMenuService {
    @Autowired
    private DiningMenuRepo menuRepo;

    @Autowired
    private VenderRepo ownerRepository;

    public DiningMenuDTO getMenuByDateAndOwner(Long OwnerId, LocalDate date) {
        DiningMenu menu = menuRepo.findByOwnerIdAndDate(OwnerId, date).orElseThrow(() -> new ResourceNotFoundException("Dining menu not found for date" + date));

        return new DiningMenuDTO(menu.getDate(), menu.getBreakfast(), menu.getLunch(), menu.getDinner());

    }

    @Transactional
    public DiningMenu insertDummyDiningMenu(Long ownerId) {
        // Retrieve an existing owner. Adjust the ownerId to one that exists in your DB.
        Owner dummyOwner = ownerRepository.findById(ownerId)
            .orElseThrow(() -> new ResourceNotFoundException("Owner with ID '" + ownerId + "' not found. Please ensure an owner with this ID exists for testing."));

        DiningMenu dummyMenu = new DiningMenu();
        dummyMenu.setDate(LocalDate.now()); // Set to today's date
        dummyMenu.setBreakfast("Breakfast: Cereals, Milk");
        dummyMenu.setLunch("Sandwich, Fruit");
        dummyMenu.setDinner("Pasta with Vegetables");
        
        dummyMenu.setOwner(dummyOwner);
        dummyMenu.setCreatedAt(LocalDateTime.now()); // Set current timestamp

        return menuRepo.save(dummyMenu);
    }


}
