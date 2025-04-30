package com.estudio.inventory_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudio.inventory_system.model.Product;
import com.estudio.inventory_system.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listAllProducts(){
        List<Product> products = productService.findAll();
        return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product newProduct){
        Product product = productService.save(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(product); //aca no deberia ser product?
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        try {
            Product product = productService.findById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProuct(@PathVariable Long id, @RequestBody Product product){
        try {
            Product existingProduct = productService.findById(id);
            product.setId(id);
            Product updateProduct = productService.save(product);
            return ResponseEntity.ok(updateProduct);
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); 
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
