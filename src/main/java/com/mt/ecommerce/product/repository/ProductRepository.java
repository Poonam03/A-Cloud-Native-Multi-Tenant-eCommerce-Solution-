package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** * Repository interface for managing Product entities.
 */
@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, UUID>, JpaRepository<Product, UUID> {

    /**
     * Finds all Product records associated with a specific vendor ID.
     *
     * @param vendorID the UUID of the vendor
     * @param pageRequest pagination information
     * @return a list of Product records
     */
    List<Product> findAllByVendorID(UUID vendorID, PageRequest pageRequest);

    /**
     * Finds all Product records associated with a specific category ID and vendor ID.
     *
     * @param categoryId the UUID of the category
     * @param vendorID the UUID of the vendor
     * @param pageRequest pagination information
     * @return a list of Product records
     */
    List<Product> findAllByCategoryIdAndVendorID(UUID categoryId, UUID vendorID, PageRequest pageRequest);

    /**
     * Finds a Product by its ID with a pessimistic write lock.
     *
     * @param id the UUID of the product
     * @return an Optional containing the found Product, or empty if not found
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findById(UUID id);



}
