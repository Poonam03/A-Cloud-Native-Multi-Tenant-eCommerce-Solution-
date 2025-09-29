package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.ImageCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/** * Repository interface for managing ImageCategory entities.
 */
@Repository
public interface ImageCategoryRepository extends JpaRepository<ImageCategory, UUID> {

}
