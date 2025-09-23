package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, UUID>,  JpaRepository<Order, UUID> {

    List<Order> findByUserID(String userID, PageRequest pageRequest);

    List<Order> findByVendorID(UUID vendorID, PageRequest pageRequest);
}
