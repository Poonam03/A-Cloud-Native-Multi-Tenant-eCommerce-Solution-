package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/** * Repository interface for managing ImageProduct entities.
 */
@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct , UUID> {
}
