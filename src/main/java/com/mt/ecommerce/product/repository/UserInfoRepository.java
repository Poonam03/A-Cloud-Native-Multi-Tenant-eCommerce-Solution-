package com.mt.ecommerce.product.repository;

import com.mt.ecommerce.product.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    Optional<UserInfo> findByEmail(String email); // Use 'email' if that is the correct field for login
}
