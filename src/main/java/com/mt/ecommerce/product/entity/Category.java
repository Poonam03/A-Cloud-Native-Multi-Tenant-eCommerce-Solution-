package com.mt.ecommerce.product.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "category_mt_t")

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "vendorID", nullable = false)
    private UUID  vendorID;

    private String name;

    private String slug;

    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ImageCategory> iamges;

    private boolean active;

    @Column(name = "created_time")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_time")
    private LocalDateTime modifiedAt;

    private String lastModifiedBy;

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getVendorID() {
        return vendorID;
    }

    public void setVendorID(UUID vendorID) {
        this.vendorID = vendorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ImageCategory> getIamges() {
        return iamges;
    }

    public void setIamges(List<ImageCategory> iamges) {
        this.iamges = iamges;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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
