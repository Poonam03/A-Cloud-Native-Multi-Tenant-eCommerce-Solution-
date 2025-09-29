package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/** * Repository interface for managing Vendor entities.
 */
@Repository
public interface VendorRepository extends JpaRepository<Vendor, UUID> {

    /**
     * Finds a Vendor by its store name.
     *
     * @param storeName the name of the store
     * @return the Vendor entity
     */
    Vendor findByStoreName(String storeName);

}
