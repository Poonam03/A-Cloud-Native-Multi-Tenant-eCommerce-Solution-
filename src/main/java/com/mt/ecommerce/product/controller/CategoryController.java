package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.model.CategoryBO;
import com.mt.ecommerce.product.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public CategoryBO addCategory(@RequestBody CategoryBO categoryBO, @AuthenticationPrincipal UserDetails userDetails) {
        return this.categoryService.addCategory(categoryBO, userDetails.getUsername());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @PutMapping(value = "", consumes = "application/json", produces = "application/json")
    public CategoryBO updateCategory(@RequestBody CategoryBO categoryBO, @AuthenticationPrincipal UserDetails userDetails) {
        this.categoryService.updateCategory(categoryBO, userDetails.getUsername());
        return categoryBO;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @DeleteMapping(value = "", consumes = "application/json")
    public void deleteCategory(@RequestParam(name = "categoryId") String categoryId, @RequestParam(name = "vendorId") String vendorId, @AuthenticationPrincipal UserDetails userDetails) {
        this.categoryService.deleteCategory(UUID.fromString(categoryId), UUID.fromString(vendorId));
    }

    @GetMapping(value = "/unsecured/all", produces = "application/json")
    public List<CategoryBO> getCategories(
                                          @RequestParam(name = "vendorId") String vendorId,
                                          @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        return this.categoryService.getCategory(UUID.fromString(vendorId), pageNo, size);
    }


}
