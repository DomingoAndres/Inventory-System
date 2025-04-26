package com.estudio.inventory_system.service;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudio.inventory_system.model.Category;
import com.estudio.inventory_system.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found."));
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }


    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }

}
