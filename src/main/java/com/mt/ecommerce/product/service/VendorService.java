package com.mt.ecommerce.product.service;

import com.mt.ecommerce.product.entity.Vendor;
import com.mt.ecommerce.product.model.VendorBO;
import com.mt.ecommerce.product.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public List<VendorBO> vendorBOS(){
        return this.vendorRepository.findAll().stream()
                .map(vendor -> {
                    VendorBO vendorBO = new VendorBO();
                    vendorBO.setId(vendor.getVendorId());
                    vendorBO.setStoreName(vendor.getStoreName());
                    return vendorBO;
                }).collect(Collectors.toList());
    }

    public VendorBO findByName(String vendorName){
        Vendor vendor = this.vendorRepository.findByStoreName(vendorName);
        if(vendor == null){
            throw new IllegalArgumentException("No vendor found");
        }
        VendorBO vendorBO = new VendorBO();
        vendorBO.setId(vendor.getVendorId());
        vendorBO.setStoreName(vendor.getStoreName());
        return vendorBO;
    }
}
