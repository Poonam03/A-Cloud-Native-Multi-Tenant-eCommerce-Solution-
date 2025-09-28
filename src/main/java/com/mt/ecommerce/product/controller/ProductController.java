package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.model.ProductBO;
import com.mt.ecommerce.product.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for managing products.
 * Provides endpoints for adding, updating, deleting, and retrieving products.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Endpoint to retrieve all products for a specific vendor.
     * Accessible without authentication.
     *
     * @param vendorId the ID of the vendor whose products are to be retrieved
     * @param page     the page number for pagination (default is 0)
     * @param size     the number of records per page (default is 10)
     * @return a list of ProductBO objects representing the vendor's products
     */
    @GetMapping(value = "/unsecured/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductBO> getAllProducts(
            @RequestParam(name = "vendorId") String vendorId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return this.productService.getProduct(UUID.fromString(vendorId),  page, size);
    }

    /**
     * Endpoint to retrieve all products for a specific vendor and category.
     * Accessible without authentication.
     *
     * @param vendorId   the ID of the vendor whose products are to be retrieved
     * @param categoryId the ID of the category to filter products
     * @param page       the page number for pagination (default is 0)
     * @param size       the number of records per page (default is 10)
     * @return a list of ProductBO objects representing the vendor's products in the specified category
     */
    @GetMapping(value = "/unsecured/category/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductBO> getAllProductsWithCategory(
            @RequestParam(name = "vendorId") String vendorId,
            @RequestParam(name = "categoryId") String categoryId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return this.productService.getProduct(UUID.fromString(vendorId),  UUID.fromString(categoryId), page, size);
    }

    /**
     * Endpoint to create a new product.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param userDetails the authenticated user's details
     * @param productBO   the product information to create
     * @return the created ProductBO
     */
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductBO createProduct(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ProductBO productBO
    ) {
        return this.productService.saveProduct(productBO, userDetails.getUsername());
    }

    /**
     * Endpoint to update an existing product.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param productBO   the product information to update
     * @param userDetails the authenticated user's details
     * @return the updated ProductBO
     */
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductBO updateProduct(@RequestBody ProductBO productBO,@AuthenticationPrincipal UserDetails userDetails) {
        this.productService.updateProduct(productBO, userDetails.getUsername());
        return productBO;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteProduct(@RequestParam(name = "id") String id) {
        this.productService.deleteProduct(UUID.fromString(id));
    }

}
