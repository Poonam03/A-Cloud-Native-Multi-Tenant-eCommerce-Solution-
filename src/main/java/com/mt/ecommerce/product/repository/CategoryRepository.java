package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.Category;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** * Repository interface for managing Category entities with pagination and sorting capabilities.
 */
@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, UUID>, JpaRepository<Category, UUID> {

    /**
     * Finds a list of categories by the given vendor ID with pagination.
     *
     * @param vendorID the UUID of the vendor
     * @param pageable the PageRequest object containing pagination information
     * @return a list of categories associated with the specified vendor ID
     */
    List<Category> findByVendorID(UUID vendorID, PageRequest pageable);

    /**
     * Finds a category by its ID and vendor ID.
     *
     * @param id the UUID of the category
     * @param vendorID the UUID of the vendor
     * @return an Optional containing the found category, or empty if not found
     */
    Optional<Category> findByIdAndVendorID(UUID id, UUID vendorID);
}
