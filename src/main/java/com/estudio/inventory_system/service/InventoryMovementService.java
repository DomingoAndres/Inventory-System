package com.estudio.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudio.inventory_system.model.InventoryMovement;
import com.estudio.inventory_system.repository.InventoryMovementRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class InventoryMovementService {

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;


    public List<InventoryMovement> findAll(){
        return inventoryMovementRepository.findAll();
    }
    
    public InventoryMovement findById(Long id){
        return inventoryMovementRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventory movement not found"));
    }

    public void deleteById(Long id){
        inventoryMovementRepository.deleteById(id);
    }


}
