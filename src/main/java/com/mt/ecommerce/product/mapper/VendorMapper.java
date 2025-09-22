package com.mt.ecommerce.product.mapper;

import com.mt.ecommerce.product.entity.Vendor;
import com.mt.ecommerce.product.model.VendorBO;

public class VendorMapper {

    public Vendor mapDao(VendorBO vendorBO){
        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorBO.getId());
        vendor.setStoreName(vendorBO.getStoreName());
        return vendor;
    }

    public VendorBO mapBo(Vendor vendor){
        VendorBO vendorBO = new VendorBO();
        vendorBO.setId(vendor.getVendorId());
        vendorBO.setStoreName(vendor.getStoreName());
        return vendorBO;
    }
}
