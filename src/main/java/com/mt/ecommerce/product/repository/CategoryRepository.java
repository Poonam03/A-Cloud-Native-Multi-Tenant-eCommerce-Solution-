package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.Category;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, UUID>, JpaRepository<Category, UUID> {

    List<Category> findByVendorID(UUID vendorID, PageRequest pageable);

    Optional<Category> findByIdAndVendorID(UUID id, UUID vendorID);
}
