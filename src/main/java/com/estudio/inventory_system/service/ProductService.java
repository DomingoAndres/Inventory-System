package com.estudio.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudio.inventory_system.model.Product;
import com.estudio.inventory_system.repository.ProductRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public void deleteById(Long id){
         productRepository.deleteById(id);
    }

    public double totalValue(Long id){
        
        Product productSearched = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found."));

        return productSearched.getStock() * productSearched.getPrice();
    }

    


}
