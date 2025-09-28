package com.mt.ecommerce.product.mapper;

import com.mt.ecommerce.product.entity.Vendor;
import com.mt.ecommerce.product.model.VendorBO;

/** * Mapper class for converting between Vendor entity and VendorBO model.
 */
public class VendorMapper {

    /**     * Maps a VendorBO object to a Vendor entity.
      *
      * @param vendorBO the VendorBO object to be mapped
      * @return the mapped Vendor entity
      */
    public Vendor mapDao(VendorBO vendorBO){
        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorBO.getId());
        vendor.setStoreName(vendorBO.getStoreName());
        return vendor;
    }

    /**     * Maps a Vendor entity to a VendorBO object.
      *
      * @param vendor the Vendor entity to be mapped
      * @return the mapped VendorBO object
      */
    public VendorBO mapBo(Vendor vendor){
        VendorBO vendorBO = new VendorBO();
        vendorBO.setId(vendor.getVendorId());
        vendorBO.setStoreName(vendor.getStoreName());
        return vendorBO;
    }
}
