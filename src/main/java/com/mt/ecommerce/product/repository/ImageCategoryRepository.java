package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.Image;
import com.mt.ecommerce.product.entity.ImageCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImageCategoryRepository extends JpaRepository<ImageCategory, UUID> {

}
