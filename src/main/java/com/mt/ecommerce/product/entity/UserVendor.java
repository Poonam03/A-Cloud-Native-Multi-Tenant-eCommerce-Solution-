package com.mt.ecommerce.product.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "user_vendor_mt_t")
@Entity
public class UserVendor {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID identification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendorId")
    private Vendor vendor;

    public UUID getIdentification() {
        return identification;
    }

    public void setIdentification(UUID identification) {
        this.identification = identification;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
