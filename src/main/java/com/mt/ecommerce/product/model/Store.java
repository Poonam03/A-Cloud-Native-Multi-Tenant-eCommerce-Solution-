package com.mt.ecommerce.product.model;

import com.mt.ecommerce.product.entity.UserInfo;
import com.mt.ecommerce.product.entity.Vendor;

import java.util.List;
import java.util.UUID;

public class Store {

    public Store() {
    }

    private UserInfoBO userInfo;

    private List<VendorBO> vendors;

    private String token;

    public UserInfoBO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBO userInfo) {
        this.userInfo = userInfo;
    }

    public List<VendorBO> getVendors() {
        return vendors;
    }

    public void setVendors(List<VendorBO> vendors) {
        this.vendors = vendors;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
