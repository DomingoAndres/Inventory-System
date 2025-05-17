package com.estudio.inventory_system.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudio.inventory_system.model.Category;
import com.estudio.inventory_system.model.InventoryMovement;
import com.estudio.inventory_system.model.Product;
import com.estudio.inventory_system.repository.CategoryRepository;
import com.estudio.inventory_system.repository.InventoryMovementRepository;
import com.estudio.inventory_system.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

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

    //obtener la categoria que se quiere cambiar por su id
    //obtener los productos de esa categoria
    //obtener los precios de esos productos
    //aplicar aumento 

    public void setPriceByPercentage(Long id, double percentage){
        
        Category categorySearched = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));

        List<Product> productsInCategory = categorySearched.getProducts();

        for(Product product : productsInCategory){
           
            product.setPrice(product.getPrice() + (product.getPrice() * percentage));
        }
    }

    //crear un metodo que desactive todos los productos de una categpria
    //cuyo stock sea menor a un valor dado

    public int changeStatusProduct(Long id, int stock){

        Category categoryBuscar = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));
        List <Product> productsIn = categoryBuscar.getProducts();
        int counter = 0;
        
        for(Product product : productsIn){
            if(product.getStock() < stock && product.isActive()){
                product.setActive(false);
                productRepository.save(product);
                counter ++;
            }
        }
        return counter;
    }

    public void changeCategory(Long idSourceCategory, Long idTargetCategory){

       
       //Seleccionamos las dos categorias que vamos a trabajar (fuente y objetivo)
        Category sourceCategory = categoryRepository.findById(idSourceCategory).orElseThrow(() -> new RuntimeException("Source category not found."));
        Category targetCategory = categoryRepository.findById(idTargetCategory).orElseThrow(() -> new RuntimeException("Target category not found."));
        List<Product> productsToMove = sourceCategory.getProducts();

        for(Product product : productsToMove){
            product.setCategory(targetCategory);
            productRepository.save(product);
        }

    }

    public void stockAdjustment(Long id, int limitStock){
        Category categoryBusqueda = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));
        List<Product> productsToAdjust = categoryBusqueda.getProducts();
        
        for(Product product : productsToAdjust){
            if(product.getStock() > limitStock){
                int diference = product.getStock() - limitStock;
                int adjustment = product.getStock() - diference;


                product.setStock(adjustment);
                productRepository.save(product);
    
                InventoryMovement adjustMovement = new InventoryMovement();

                adjustMovement.createInvMov(product, diference, "AJUSTE");
    
                inventoryMovementRepository.save(adjustMovement);
            }

        }
    }

    public void stockIncrement(Long id, int cantidad){
        Category categoryToIncrement = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));
        
        List<Product> products = categoryToIncrement.getProducts();

        int newStock;

        
        for(Product product : products){
            if(product.isActive()){
                newStock = product.getStock() + cantidad;
                product.setStock(newStock);
                productRepository.save(product);


                InventoryMovement incrementMovement = new InventoryMovement();

                incrementMovement.createInvMov(product, newStock, "INCREMENTO");

                inventoryMovementRepository.save(incrementMovement);
            }    
        }



    }
    

}
