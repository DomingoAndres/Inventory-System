package com.estudio.inventory_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estudio.inventory_system.model.InventoryMovement;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long>{

}
