package com.mt.ecommerce.product.service;

import com.mt.ecommerce.product.entity.Category;
import com.mt.ecommerce.product.entity.Image;
import com.mt.ecommerce.product.entity.ImageCategory;
import com.mt.ecommerce.product.exception.CategoryNotFoundException;
import com.mt.ecommerce.product.model.CategoryBO;
import com.mt.ecommerce.product.model.ImageBO;
import com.mt.ecommerce.product.repository.CategoryRepository;
import com.mt.ecommerce.product.repository.ImageCategoryRepository;
import com.mt.ecommerce.product.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ImageRepository imageRepository;

    private final ImageCategoryRepository imageCategoryRepository;

    public CategoryService(CategoryRepository categoryRepository, ImageRepository imageRepository, ImageCategoryRepository imageCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.imageCategoryRepository = imageCategoryRepository;
    }

    @Transactional
    public CategoryBO addCategory(CategoryBO categoryBO, String userName) {
        Category category = new Category();
        category.setName(categoryBO.getName());
        category.setVendorID(categoryBO.getVendorID());
        category.setSlug(categoryBO.getSlug());
        category.setDescription(categoryBO.getDescription());
        category.setActive(categoryBO.isActive());
        category.setLastModifiedBy(userName);
        List<ImageCategory> imageCategories = new ArrayList<>();
        if (categoryBO.getImageBOS() != null && !categoryBO.getImageBOS().isEmpty()) {
            categoryBO.getImageBOS().forEach(imageBO -> {
                ImageCategory imageCategory = new ImageCategory();
                imageCategory.setId(UUID.randomUUID());
                imageCategory.setImageId(imageBO.getId());
                imageCategory.setCategory(category);
                imageCategories.add(imageCategory);
            });
        }
        category.setIamges(imageCategories);
        Category category1 = this.categoryRepository.save(category);
        categoryBO.setId(category1.getId());
        return categoryBO;
    }

    public List<CategoryBO> getCategory(UUID vendorId, int pageNo, int size, String username) {
        return this.categoryRepository
                .findByVendorID(vendorId, PageRequest.of(pageNo, size))
                .stream()
                .map(category -> {
                    CategoryBO categoryBO = new CategoryBO();
                    categoryBO.setId(category.getId());
                    categoryBO.setName(category.getName());
                    categoryBO.setVendorID(category.getVendorID());
                    categoryBO.setSlug(category.getSlug());
                    categoryBO.setDescription(category.getDescription());
                    categoryBO.setActive(category.isActive());
                    List<Image> imageCategories = this.imageRepository.findByUserId(username);
                    List<ImageBO> imageBOS = new ArrayList<>();
                    if (imageCategories != null && !imageCategories.isEmpty()) {
                        imageCategories.forEach(imageCategory -> {
                            ImageBO imageBO = new ImageBO();
                            imageBO.setId(imageCategory.getId());
                            imageBO.setImageUrl(imageCategory.getImageUrl());
                            imageBO.setAltText(imageCategory.getAltText());
                            imageBOS.add(imageBO);
                        });
                    }
                    categoryBO.setImageBOS(imageBOS);
                    return categoryBO;
                })
                .collect(Collectors.toList());
    }

    public void updateCategory(CategoryBO categoryBO, String userName) {
        Optional<Category> categoryOptional = this.categoryRepository.findByIdAndVendorID(categoryBO.getId(), categoryBO.getVendorID());
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setName(categoryBO.getName());
            category.setLastModifiedBy(userName);
            category.setSlug(categoryBO.getSlug());
            category.setDescription(categoryBO.getDescription());
            category.setActive(categoryBO.isActive());
            this.categoryRepository.save(category);
        } else {
            throw new CategoryNotFoundException("Category is not there in DB for this vendor");
        }
    }

    public void deleteCategory(UUID categoryId, UUID vendorId) {
        Optional<Category> categoryOptional = this.categoryRepository.findByIdAndVendorID(categoryId, vendorId);
        if (categoryOptional.isPresent()) {
            this.categoryRepository.deleteById(categoryId);
        } else {
            throw new CategoryNotFoundException("Category is not there in DB for this vendor");
        }
    }
}
