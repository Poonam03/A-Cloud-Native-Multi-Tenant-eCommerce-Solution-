package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/** * Repository interface for managing UserInfo entities.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {

    /**
     * Finds a UserInfo record by its email.
     *
     * @param email the email of the user
     * @return an Optional containing the UserInfo if found, or empty if not found
     */
    Optional<UserInfo> findByEmail(String email); // Use 'email' if that is the correct field for login
}
