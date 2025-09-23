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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ImageCategoryRepository imageCategoryRepository;

    private final ImageRepository imageRepository;

    public CategoryService(CategoryRepository categoryRepository, ImageCategoryRepository imageCategoryRepository, ImageRepository imageRepository) {
        this.categoryRepository = categoryRepository;
        this.imageCategoryRepository = imageCategoryRepository;
        this.imageRepository = imageRepository;
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
                imageCategory.setImageId(imageBO.getId());
                imageCategory.setCategory(category);
                imageCategories.add(imageCategory);
            });
        }
        category.setIamges(imageCategories);
        Category category1 = this.categoryRepository.save(category);
        this.imageCategoryRepository.saveAll(imageCategories);
        categoryBO.setId(category1.getId());
        return categoryBO;
    }

    public List<CategoryBO> getCategory(UUID vendorId, int pageNo, int size) {
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

                    List<ImageBO> imageBOS =
                            category.getIamges()
                                    .stream().map(imageCategory -> {
                                        Image image = this.imageRepository.findById(imageCategory.getImageId()).orElse(null);
                                        if (Objects.nonNull(image)) {
                                            ImageBO imageBO = new ImageBO();
                                            imageBO.setImageUrl(image.getImageUrl());
                                            imageBO.setId(image.getId());
                                            imageBO.setAltText(image.getAltText());
                                            imageBO.setUserId(image.getUserId());
                                            return imageBO;
                                        } else {
                                            return null;
                                        }
                                    })
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());
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
