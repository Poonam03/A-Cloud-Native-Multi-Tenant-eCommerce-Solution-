package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.Order;
import com.mt.ecommerce.product.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, UUID> {

    List<OrderProductEntity> findAllByOrderId(UUID orderId);
}
