package com.mt.ecommerce.product.service;

import com.mt.ecommerce.product.entity.Image;
import com.mt.ecommerce.product.entity.ImageProduct;
import com.mt.ecommerce.product.entity.Product;
import com.mt.ecommerce.product.exception.ProductNotFoundException;
import com.mt.ecommerce.product.model.ImageBO;
import com.mt.ecommerce.product.model.ProductBO;
import com.mt.ecommerce.product.repository.ImageProductRepository;
import com.mt.ecommerce.product.repository.ImageRepository;
import com.mt.ecommerce.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ImageProductRepository imageProductRepository;

    private final ImageRepository imageRepository;


    public ProductService(ProductRepository productRepository, ImageProductRepository imageProductRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.imageProductRepository = imageProductRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public void updateProduct(ProductBO productBO, String userName) {
        Optional<Product> productOptional = this.productRepository.findById(productBO.getId());
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product id not found " + productBO.getId().toString());
        }
        Product product = productOptional.get();
        product.setModifiedBy(userName);
        Optional.ofNullable(productBO.getQuantity())
                .ifPresent(product::setStockQuantity);

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

        this.productRepository.save(productOptional.get());
    }


    @Transactional
    public ProductBO saveProduct(ProductBO productBO, String userName) {
        Product product = new Product();
        product.setVendorID(productBO.getVendorId());
        product.setCategoryId(productBO.getCategoryId());
        product.setName(productBO.getName());
        product.setSlug(productBO.getSlug());
        product.setDescription(productBO.getDescription());
        product.setSku(productBO.getSku());
        product.setPrice(productBO.getPrice());
        product.setStockQuantity(productBO.getQuantity());
        product.setActive(true);
        product.setModifiedBy(userName);
        List<ImageProduct> imageProducts = new ArrayList<>();
        if (productBO.getImageBOS() != null && !productBO.getImageBOS().isEmpty()) {
            productBO.getImageBOS().forEach(imageBO -> {
                ImageProduct imageCategory = new ImageProduct();
                imageCategory.setImageId(imageBO.getId());
                imageCategory.setProduct(product);
                imageProducts.add(imageCategory);
            });
        }
        product.setImageProducts(imageProducts);
        Product product1 = this.productRepository.save(product);
        this.imageProductRepository.saveAll(imageProducts);
        productBO.setId(product1.getId());
        return productBO;
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        Optional<Product> productOptional = this.productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product id not found " + productId);
        }
        this.productRepository.delete(productOptional.get());
    }

    public List<ProductBO> getProduct(UUID vendorId,  int pageNo, int size) {
        return this.productRepository
                .findByVendorID(vendorId,  org.springframework.data.domain.PageRequest.of(pageNo, size))
                .stream()
                .map(product -> {
                    ProductBO productBO = new ProductBO();
                    productBO.setId(product.getId());
                    productBO.setVendorId(product.getVendorID());
                    productBO.setCategoryId(product.getCategoryId());
                    productBO.setName(product.getName());
                    productBO.setSlug(product.getSlug());
                    productBO.setDescription(product.getDescription());
                    productBO.setSku(product.getSku());
                    productBO.setPrice(product.getPrice());
                    productBO.setQuantity(product.getStockQuantity());
                    List<com.mt.ecommerce.product.model.ImageBO> imageBOS = product.getImageProducts()
                            .stream()
                            .map(imageProduct -> {
                                Image image = this.imageRepository.findById(imageProduct.getImageId()).orElse(null);
                                if(Objects.nonNull(image)){
                                    ImageBO imageBO = new ImageBO();
                                    imageBO.setImageUrl(image.getImageUrl());
                                    imageBO.setId(image.getId());
                                    imageBO.setAltText(image.getAltText());
                                    imageBO.setUserId(image.getUserId());
                                    return imageBO;
                                }
                                return null;
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    productBO.setImages(imageBOS);
                    return productBO;
                })
                .collect(Collectors.toList());
    }




}
