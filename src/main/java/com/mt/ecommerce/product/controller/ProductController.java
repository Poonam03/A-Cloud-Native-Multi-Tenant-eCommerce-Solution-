package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.model.ProductBO;
import com.mt.ecommerce.product.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void getProduct() {

    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void getAllProducts(
            @RequestParam(name = "vendorId") String vendorId,
            @RequestParam(name = "categoryId") String categoryId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {

    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductBO createProduct(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "categoryId") String categoryId,
            @RequestBody ProductBO productBO
    ) {
        return this.productService.saveProduct(productBO, userDetails.getUsername(), UUID.fromString(categoryId));
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductBO updateProduct(@RequestBody ProductBO productBO) {
        this.productService.updateProduct(productBO);
        return productBO;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteProduct(@RequestParam(name = "id") String id) {
        this.productService.deleteProduct(UUID.fromString(id));

    }

}
