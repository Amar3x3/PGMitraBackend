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

        return new DiningMenuDTO(menu.getDate(), menu.getText());

    }

    @Transactional
    public DiningMenu insertDummyDiningMenu(Long ownerId) {

        Owner dummyOwner = ownerRepository.findById(ownerId)
            .orElseThrow(() -> new ResourceNotFoundException("Owner with ID '" + ownerId + "' not found. Please ensure an owner with this ID exists for testing."));

        DiningMenu dummyMenu = new DiningMenu();
        dummyMenu.setDate(LocalDate.now());
        dummyMenu.setText("Dummy Menu for Testing:\n" +
                          "Breakfast: Cereals, Milk\n" +
                          "Lunch: Sandwich, Fruit\n" +
                          "Dinner: Pasta with Vegetables");
        dummyMenu.setOwner(dummyOwner);
        dummyMenu.setCreatedAt(LocalDateTime.now());

        return menuRepo.save(dummyMenu);
    }


}
