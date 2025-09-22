package com.mt.ecommerce.product.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vendor_mt_t")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID vendorId;


    @Column(name = "store_name", nullable = false, unique = true)
    private String storeName;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserVendor> userVendors;

    @Column(name = "created_time")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_time")
    private LocalDateTime modifiedAt;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public UUID getVendorId() {
        return vendorId;
    }

    public void setVendorId(UUID vendorId) {
        this.vendorId = vendorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public List<UserVendor> getUserVendors() {
        return userVendors;
    }

    public void setUserVendors(List<UserVendor> userVendors) {
        this.userVendors = userVendors;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
