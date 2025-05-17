package com.estudio.inventory_system.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudio.inventory_system.model.InventoryMovement;
import com.estudio.inventory_system.repository.InventoryMovementRepository;
import com.estudio.inventory_system.repository.ProductRepository;

import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;

import com.estudio.inventory_system.model.Product;

@Service
@Transactional
public class InventoryMovementService {

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    @Autowired
    private ProductRepository productRepository;


    public List<InventoryMovement> findAll(){
        return inventoryMovementRepository.findAll();
    }
    
    public InventoryMovement findById(Long id){
        return inventoryMovementRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventory movement not found"));
    }

    public void deleteById(Long id){
        inventoryMovementRepository.deleteById(id);
    }

    public InventoryMovement save(InventoryMovement inventoryMovement){
        
        Product product = productRepository.findById(inventoryMovement.getProduct().getId()).get();
        if(product != null){
            int currentStock = product.getStock();
            
            if (inventoryMovement.getType().equals("ENTRADA")){

                product.setStock(currentStock + inventoryMovement.getQuantity());

            }else if(inventoryMovement.getType().equals("SALIDA")){
                if(currentStock > inventoryMovement.getQuantity()){
                    product.setStock(currentStock - inventoryMovement.getQuantity());
                }
                throw new RuntimeException("current stock needs to be higger than out quantity");
            }
            productRepository.save(product);
            
            
            return inventoryMovementRepository.save(inventoryMovement);
        }
        throw new RuntimeException("Product not found");
    }


    //transferencia de stock de un producto a otro
    //stock minimo para transferir no existe
    //alguno de los productos no existe


    //id producto fuente y destino, cantidad que se va a mover

    //metodo de transferencia: recibe id producto fuente, id producto destino y su cantidad

    public void transferStock(Long idSourceProduct, Long idTargetProduct, int quantity){
        
        Product sourceProduct = productRepository.findById(idSourceProduct).get();
        
        Product targetProduct = productRepository.findById(idTargetProduct).get();

        if(sourceProduct.getStock() < quantity){
            throw new RuntimeException("Not enought stock in the source product");
        }

        //Movimiento de salida

        InventoryMovement exitMovement = new InventoryMovement();

        exitMovement.setProduct(sourceProduct);
        exitMovement.setQuantity(quantity);
        exitMovement.setType("SALIDA");
        exitMovement.setDate(new Date());
        sourceProduct.setStock(sourceProduct.getStock() - quantity);

        //Movimiento de entrada

        InventoryMovement entryMovement = new InventoryMovement();

        entryMovement.setProduct(targetProduct);
        entryMovement.setQuantity(quantity);
        entryMovement.setType("ENTRADA");
        entryMovement.setDate(new Date());
        targetProduct.setStock(targetProduct.getStock() + quantity);

        //guardar productos y movimientos

        inventoryMovementRepository.save(exitMovement);
        inventoryMovementRepository.save(entryMovement);
        productRepository.save(targetProduct);
        productRepository.save(sourceProduct);
    }


}
