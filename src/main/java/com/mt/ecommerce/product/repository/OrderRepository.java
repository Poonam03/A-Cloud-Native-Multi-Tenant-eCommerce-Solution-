package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/** * Repository interface for managing Order entities.
 */
@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, UUID>,  JpaRepository<Order, UUID> {

    /**
     * Finds orders by user ID with pagination support.
     *
     * @param userId the ID of the user
     * @param pageRequest the pagination information
     * @return a list of orders associated with the specified user ID
     */
    List<Order> findByUserId(String userId, PageRequest pageRequest);

    /**
     * Finds orders by vendor ID with pagination support.
     *
     * @param vendorId the UUID of the vendor
     * @param pageRequest the pagination information
     * @return a list of orders associated with the specified vendor ID
     */
    List<Order> findByVendorId(UUID vendorId, PageRequest pageRequest);
}
