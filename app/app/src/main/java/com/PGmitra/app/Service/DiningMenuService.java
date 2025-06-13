package com.PGmitra.app.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PGmitra.app.DTO.DiningMenuDTO;
import com.PGmitra.app.Entity.DiningMenu;
import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Repository.DiningMenuRepo;
import com.PGmitra.app.Repository.TenantRepo;
import com.PGmitra.app.Repository.VenderRepo;

import jakarta.transaction.Transactional;

@Service
public class DiningMenuService {
    @Autowired
    private DiningMenuRepo menuRepo;

    @Autowired
    private VenderRepo ownerRepository;

    @Autowired
    private TenantRepo tenantRepo;

    public DiningMenuDTO getMenuByDateAndOwner(Long OwnerId, LocalDate date) {
        DiningMenu menu = menuRepo.findByOwnerIdAndDate(OwnerId, date).orElseThrow(() -> new ResourceNotFoundException("Dining menu not found for date" + date));


        return new DiningMenuDTO(menu.getId(), menu.getDate(), menu.getBreakfast(), menu.getLunch(), menu.getDinner(), menu.getOwner().getId());


    }

    @Transactional
    public DiningMenu insertDummyDiningMenu(Long ownerId) {

        Owner dummyOwner = ownerRepository.findById(ownerId)
            .orElseThrow(() -> new ResourceNotFoundException("Owner with ID '" + ownerId + "' not found. Please ensure an owner with this ID exists for testing."));

        DiningMenu dummyMenu = new DiningMenu();
        dummyMenu.setDate(LocalDate.now()); // Set to today's date
        dummyMenu.setBreakfast("Breakfast: Cereals, Milk");
        dummyMenu.setLunch("Sandwich, Fruit");
        dummyMenu.setDinner("Pasta with Vegetables");
        
        dummyMenu.setOwner(dummyOwner);
        dummyMenu.setCreatedAt(LocalDateTime.now());

        return menuRepo.save(dummyMenu);
    }

    public void createMenu(DiningMenuDTO dto) {
        Owner owner = ownerRepository.findById(dto.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Owner not found with ID:" + dto.getOwnerId()));

        DiningMenu menu = new DiningMenu();
        menu.setDate(dto.getDate());
        menu.setBreakfast(dto.getBreakfast());
        menu.setLunch(dto.getLunch());
        menu.setDinner(dto.getDinner());
        menu.setOwner(owner);
        menuRepo.save(menu);
    }

    public void editMenu(DiningMenuDTO dto) {
        DiningMenu menu = menuRepo.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException("Menu with not found with ID: " + dto.getId()));
        if (dto.getBreakfast() != null) {
            menu.setBreakfast(dto.getBreakfast());
        }
        if (dto.getLunch() != null) {
            menu.setLunch(dto.getLunch());
        }
        if (dto.getDinner() != null) {
            menu.setDinner(dto.getDinner());
        }
        menuRepo.save(menu);
    }

    public DiningMenuDTO getMenuByDateAndTenant(Long tenantId) {
        Tenant tenant = tenantRepo.findById(tenantId).orElseThrow(() -> new ResourceNotFoundException("No menu associated with tenant with ID: " + tenantId));
        Owner owner =null;
        if (tenant.getOwner() != null) {
            owner = tenant.getOwner();
        } else {
            throw new ResourceNotFoundException("No owner associated with the tenant with ID:" + tenantId);
        }

        DiningMenu menu = menuRepo.findByOwnerIdAndDate(owner.getId(), LocalDate.now()).orElseThrow(() -> new ResourceNotFoundException("Dining menu not found for date" + LocalDate.now()));
        
        return new DiningMenuDTO(menu.getId(), menu.getDate(), menu.getBreakfast(), menu.getLunch(), menu.getDinner(), menu.getOwner().getId());
    }


}
