package com.mt.ecommerce.product.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "category_mt_t")
public class Category {

    @Id
    private UUID id;

    @Column(name = "vendor_id", nullable = false)
    private UUID vendorId;

    private String name;

    private String slug;

    private String description;

    @Column(name = "parent_category_id")
    private UUID parentCategoryId;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ImageCategory> iamges;

    private boolean active;

    @Column(name = "created_time")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_time")
    private LocalDateTime modifiedAt;


}
