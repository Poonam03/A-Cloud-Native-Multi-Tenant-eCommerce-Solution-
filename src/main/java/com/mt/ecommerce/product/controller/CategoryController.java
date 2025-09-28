package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.model.CategoryBO;
import com.mt.ecommerce.product.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for managing product categories.
 * Provides endpoints for adding, updating, deleting, and retrieving categories.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Endpoint to add a new category.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param categoryBO   the category information to add
     * @param userDetails  the authenticated user's details
     * @return the added CategoryBO
     */
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public CategoryBO addCategory(@RequestBody CategoryBO categoryBO, @AuthenticationPrincipal UserDetails userDetails) {
        return this.categoryService.addCategory(categoryBO, userDetails.getUsername());
    }

    /**
     * Endpoint to update an existing category.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param categoryBO   the category information to update
     * @param userDetails  the authenticated user's details
     * @return the updated CategoryBO
     */
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @PutMapping(value = "", consumes = "application/json", produces = "application/json")
    public CategoryBO updateCategory(@RequestBody CategoryBO categoryBO, @AuthenticationPrincipal UserDetails userDetails) {
        this.categoryService.updateCategory(categoryBO, userDetails.getUsername());
        return categoryBO;
    }

    /**
     * Endpoint to delete a category.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param categoryId   the ID of the category to delete
     * @param vendorId     the ID of the vendor
     * @param userDetails  the authenticated user's details
     */
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @DeleteMapping(value = "", consumes = "application/json")
    public void deleteCategory(@RequestParam(name = "categoryId") String categoryId, @RequestParam(name = "vendorId") String vendorId, @AuthenticationPrincipal UserDetails userDetails) {
        this.categoryService.deleteCategory(UUID.fromString(categoryId), UUID.fromString(vendorId));
    }

    /**
     * Endpoint to retrieve categories for a specific vendor with pagination.
     * This endpoint is unsecured and can be accessed without authentication.
     *
     * @param vendorId the ID of the vendor
     * @param pageNo   the page number for pagination (default is 0)
     * @param size     the number of items per page (default is 10)
     * @return a list of CategoryBO objects
     */
    @GetMapping(value = "/unsecured/all", produces = "application/json")
    public List<CategoryBO> getCategories(
                                          @RequestParam(name = "vendorId") String vendorId,
                                          @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        return this.categoryService.getCategory(UUID.fromString(vendorId), pageNo, size);
    }


}
