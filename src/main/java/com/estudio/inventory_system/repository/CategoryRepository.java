package com.estudio.inventory_system.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.estudio.inventory_system.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
