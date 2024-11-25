package com.demo.bookstore.controller;

import com.demo.bookstore.dto.InventoryCheckDTO;
import com.demo.bookstore.dto.InventoryQueryDTO;
import com.demo.bookstore.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/check")
    public ResponseEntity<InventoryCheckDTO> checkStock(@RequestBody InventoryQueryDTO inventoryQueryDTO) {
        boolean b = inventoryService.checkStock(inventoryQueryDTO.getBookId(), inventoryQueryDTO.getQuantity());
        return ResponseEntity.ok(new InventoryCheckDTO(b));
    }

    @PutMapping(value = "/reduce")
    public void reduceStock(@RequestBody InventoryQueryDTO inventoryQueryDTO) {
        inventoryService.reduceStock(inventoryQueryDTO.getBookId(), inventoryQueryDTO.getQuantity());
    }

    @PutMapping(value = "/increase")
    public void increaseStock(@RequestBody InventoryQueryDTO inventoryQueryDTO) {
        inventoryService.increaseStock(inventoryQueryDTO.getBookId(), inventoryQueryDTO.getQuantity());
    }
}
