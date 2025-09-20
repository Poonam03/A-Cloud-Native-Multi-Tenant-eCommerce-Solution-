package com.mt.ecommerce.product.service;

import com.mt.ecommerce.product.entity.Product;
import com.mt.ecommerce.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public void updateProduct(com.mt.ecommerce.product.model.ProductBO productBO){
        Optional<Product> productOptional = this.productRepository.findById(productBO.getId());

        if(productOptional.isEmpty()){

        }
        //update product

        this.productRepository.save(productOptional.get());
    }


}
