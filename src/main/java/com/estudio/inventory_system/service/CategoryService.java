package com.estudio.inventory_system.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudio.inventory_system.model.Category;
import com.estudio.inventory_system.model.Product;
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

    //metodo booleano, indica si cierta categoria tiene prodcuto con stock bajo
    //primero se encuntra la categoria que queremos consultar
    //despues se revisa todos los objetos ddentro de la categoria
    //revisamos si la cantidad o stock del producto es menor al stock que se pasa como parametro
    //retornamos true si hay objeto de categoria bajo o false si esta altp

    public boolean hasLowStock(Long idCategory, int stockLow){

        Category category = categoryRepository.findById(idCategory).orElseThrow(() -> new RuntimeException("Category not found."));


        List<Product> products = category.getProducts();

        for(Product product : products){
            if(product.getStock() < stockLow) return true;
        }
        return false;
    }

}
