package com.mt.ecommerce.product.service;

import com.mt.ecommerce.product.entity.Vendor;
import com.mt.ecommerce.product.model.VendorBO;
import com.mt.ecommerce.product.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/** * Service class for managing vendors.
 * Provides methods for retrieving vendor information.
 */
@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    /**     * Retrieves all vendors.
     *
     * @return a list of VendorBO objects representing all vendors
     */
    public List<VendorBO> vendorBOS(){
        return this.vendorRepository.findAll().stream()
                .map(vendor -> {
                    VendorBO vendorBO = new VendorBO();
                    vendorBO.setId(vendor.getVendorId());
                    vendorBO.setStoreName(vendor.getStoreName());
                    return vendorBO;
                }).collect(Collectors.toList());
    }

    /**     * Finds a vendor by store name.
     *
     * @param vendorName the store name of the vendor to find
     * @return the VendorBO object representing the found vendor
     * @throws IllegalArgumentException if no vendor is found with the specified store name
     */
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
