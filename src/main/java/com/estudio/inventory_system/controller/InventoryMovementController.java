package com.estudio.inventory_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudio.inventory_system.model.InventoryMovement;
import com.estudio.inventory_system.service.InventoryMovementService;

@RestController
@RequestMapping("/api/v1/inventoryMovement")
public class InventoryMovementController {

    @Autowired
    private InventoryMovementService inventoryMovementService;

    @GetMapping
    public ResponseEntity<List<InventoryMovement>> listAllInventoryMovements(){
        List<InventoryMovement> inventoryMovements =  inventoryMovementService.findAll();
        return inventoryMovements.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(inventoryMovements);
    }

    //ACA VA EL METODO PARA EL SAVE JUJU

    @GetMapping("{id}")
    public ResponseEntity<InventoryMovement> findById(@PathVariable Long id){
        try {
            InventoryMovement movementSearched = inventoryMovementService.findById(id);
            return ResponseEntity.ok(movementSearched);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    //ACA VA EL METODO PARA EL UPDATE EQUISDE

    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteByid(@PathVariable Long id){
        try {
            inventoryMovementService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
