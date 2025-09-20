package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.model.ProductBO;
import com.mt.ecommerce.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("mt/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.APPLICATION_JSON_VALUE)
    public void getProduct() {

    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.APPLICATION_JSON_VALUE)
    public void getAllProducts(
            @RequestParam(name = "vendorId") String vendorId,
            @RequestParam(name = "categoryId") String categoryId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {

    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ProductBO createProduct(
            @RequestParam(name = "vendorId") String vendorId,
            @RequestParam(name = "categoryId") String categoryId,
            @RequestBody ProductBO productBO
    ) {
        return this.productService.saveProduct(productBO, UUID.fromString(vendorId), UUID.fromString(categoryId));
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ProductBO updateProduct(@RequestBody ProductBO productBO) {
        this.productService.updateProduct(productBO);
        return productBO;
    }

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.APPLICATION_JSON_VALUE)
    public void deleteProduct(@RequestParam(name = "id") String id) {
        this.productService.deleteProduct(UUID.fromString(id));

    }

}
