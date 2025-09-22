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
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
    public ProductBO saveProduct(ProductBO productBO, String userName) {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setVendorID(productBO.getVendorId());
        product.setCategoryId(productBO.getCategoryId());
        product.setName(productBO.getName());
        product.setSlug(productBO.getSlug());
        product.setDescription(productBO.getDescription());
        product.setSku(productBO.getSku());
        product.setPrice(productBO.getPrice());
        product.setStockQuantity(Integer.parseInt(productBO.getQuantity()));
        product.setActive(true);
        product.setModifiedBy(userName);
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
        Product product1 = this.productRepository.save(product);
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

    public List<ProductBO> getProduct(UUID vendorId, UUID categoryId, int pageNo, int size) {
        return this.productRepository
                .findByVendorIdAndCategoryId(vendorId, categoryId, org.springframework.data.domain.PageRequest.of(pageNo, size))
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
                    productBO.setQuantity(String.valueOf(product.getStockQuantity()));
                    List<com.mt.ecommerce.product.model.ImageBO> imageBOS = product.getImageProducts()
                            .stream()
                            .map(imageProduct -> {
                                com.mt.ecommerce.product.model.ImageBO imageBO = new com.mt.ecommerce.product.model.ImageBO();
                                imageBO.setId(imageProduct.getImageId());
                                return imageBO;
                            })
                            .collect(Collectors.toList());
                    productBO.setImages(imageBOS);
                    return productBO;
                })
                .collect(Collectors.toList());
    }




}
