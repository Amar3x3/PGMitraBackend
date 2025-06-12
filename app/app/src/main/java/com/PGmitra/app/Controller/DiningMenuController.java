package com.PGmitra.app.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PGmitra.app.DTO.DiningMenuDTO;
import com.PGmitra.app.Entity.DiningMenu;
import com.PGmitra.app.Exception.ResourceNotFoundException;
import com.PGmitra.app.Service.DiningMenuService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/Dining")
public class DiningMenuController {
    @Autowired
    private DiningMenuService diningMenuService;

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<DiningMenuDTO> getMenuByDate(@PathVariable("ownerId") Long ownerId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        DiningMenuDTO menu = diningMenuService.getMenuByDateAndOwner(ownerId, date);
        return ResponseEntity.ok(menu);
    }


    @PostMapping("/dummy/{ownerId}")
    public ResponseEntity<DiningMenu> createDummyDiningMenu(@PathVariable Long ownerId) {
        DiningMenu dummyMenu = diningMenuService.insertDummyDiningMenu(ownerId);
        return new ResponseEntity<>(dummyMenu, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createMenu(@RequestBody DiningMenuDTO dto) {
        try {
            diningMenuService.createMenu(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Menu created succesfully");
        } catch(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<Object> editMenu(@RequestBody DiningMenuDTO dto) {
        try {
            diningMenuService.editMenu(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Menu edited succesfully");
        } catch(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<Object> getTenantMenuByDate(@PathVariable Long tenantId) {
        try {
            DiningMenuDTO menu = diningMenuService.getMenuByDateAndTenant(tenantId);
            return ResponseEntity.ok(menu);
        } catch(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
    
    
    
}
