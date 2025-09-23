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

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, UUID>, JpaRepository<Product, UUID> {

    List<Product> findByVendorID(UUID vendorID,  PageRequest pageRequest);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findById(UUID id);



}
