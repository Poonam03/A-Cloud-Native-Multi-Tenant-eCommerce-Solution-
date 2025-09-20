package com.mt.ecommerce.product.service;

import com.mt.ecommerce.product.entity.ImageProduct;
import com.mt.ecommerce.product.entity.Product;
import com.mt.ecommerce.product.exception.ProductNotFoundException;
import com.mt.ecommerce.product.model.ProductBO;
import com.mt.ecommerce.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Transactional
    public void updateProduct(ProductBO productBO) {
        Optional<Product> productOptional = this.productRepository.findById(productBO.getId());
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product id not found " + productBO.getId().toString());
        }
        Product product = productOptional.get();
        Optional.ofNullable(productBO.getQuantity())
                .ifPresent(quant -> product.setStockQuantity(Integer.parseInt(quant)));

        Optional.ofNullable(productBO.getPrice())
                .ifPresent(product::setPrice);

        Optional.ofNullable(productBO.getName())
                .ifPresent(product::setName);

        Optional.ofNullable(productBO.getSlug())
                .ifPresent(product::setSlug);

        Optional.ofNullable(productBO.getDescription())
                .ifPresent(product::setDescription);

        Optional.ofNullable(productBO.getSku())
                .ifPresent(product::setSku);

        Optional.ofNullable(productBO.getImages())
                .ifPresent(images -> {
                    product.getImageProducts().clear();
                    List<ImageProduct> imageProducts = new ArrayList<>();

                    productBO.getImages().forEach(image -> {
                        ImageProduct imageProduct = new ImageProduct();
                        imageProduct.setIdentification(java.util.UUID.randomUUID());
                        imageProduct.setImageId(image.getId());
                        imageProduct.setProduct(product);
                        imageProducts.add(imageProduct);
                    });
                    product.setImageProducts(imageProducts);
                });

        this.productRepository.save(productOptional.get());
    }


    @Transactional
    public ProductBO saveProduct(ProductBO productBO, UUID vendorId, UUID categoryId) {
        //TODO: need to check if vendor and category exits

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setVendorId(vendorId);
        product.setCategoryId(categoryId);
        product.setName(productBO.getName());
        product.setSlug(productBO.getSlug());
        product.setDescription(productBO.getDescription());
        product.setSku(productBO.getSku());
        product.setPrice(productBO.getPrice());
        product.setStockQuantity(Integer.parseInt(productBO.getQuantity()));
        product.setActive(true);

        List<ImageProduct> imageProducts = new ArrayList<>();
        if (productBO.getImages() != null) {
            productBO.getImages().forEach(image -> {
                ImageProduct imageProduct = new ImageProduct();
                imageProduct.setIdentification(UUID.randomUUID());
                imageProduct.setImageId(image.getId());
                imageProduct.setProduct(product);
                imageProducts.add(imageProduct);
            });
        }
        product.setImageProducts(imageProducts);

        this.productRepository.save(product);

        ProductBO productBO1 = new ProductBO();
        productBO1.setId(product.getId());
        productBO1.setName(product.getName());
        productBO1.setSlug(product.getSlug());
        productBO1.setDescription(product.getDescription());
        productBO1.setSku(product.getSku());
        productBO1.setPrice(product.getPrice());
        productBO1.setQuantity(String.valueOf(product.getStockQuantity()));
        productBO1.setImages(productBO.getImages());
        return productBO1;
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        Optional<Product> productOptional = this.productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product id not found " + productId);
        }
        this.productRepository.delete(productOptional.get());
    }

}
