package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/** * Repository interface for managing Image entities.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

    /**
     * Finds images by user ID.
     *
     * @param userId the user ID
     * @return a list of images associated with the specified user ID
     */
    List<Image> findByUserId(String userId);
}
