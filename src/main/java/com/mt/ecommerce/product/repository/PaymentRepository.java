package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findAllByCreditorId(UUID creditorId);
}
