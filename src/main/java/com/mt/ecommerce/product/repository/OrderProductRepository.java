package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.Order;
import com.mt.ecommerce.product.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/** * Repository interface for managing OrderProductEntity entities.
 */
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, UUID> {

    /**
     * Finds all OrderProductEntity records associated with a specific order ID.
     *
     * @param orderId the UUID of the order
     * @return a list of OrderProductEntity records
     */
    List<OrderProductEntity> findAllByOrderId(UUID orderId);
}
