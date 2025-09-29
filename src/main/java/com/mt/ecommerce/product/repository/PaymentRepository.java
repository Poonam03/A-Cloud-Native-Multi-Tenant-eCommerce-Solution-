package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/** * Repository interface for managing Payment entities.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    /**
     * Finds all Payment records associated with a specific creditor ID.
     *
     * @param creditorId the UUID of the creditor
     * @return a list of Payment records
     */
    List<Payment> findAllByCreditorId(UUID creditorId);
}
