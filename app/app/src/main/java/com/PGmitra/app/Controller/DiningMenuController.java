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
import com.PGmitra.app.Service.DiningMenuService;

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
}
