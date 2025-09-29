package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.UserVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/** * Repository interface for managing UserVendor entities.
 */
@Repository
public interface UserVendorRepository extends JpaRepository<UserVendor , UUID> {
}
