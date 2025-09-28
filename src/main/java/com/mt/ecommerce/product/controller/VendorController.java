package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.model.VendorBO;
import com.mt.ecommerce.product.service.VendorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for managing vendors.
 * Provides endpoints for retrieving vendor information.
 */
@RestController
@RequestMapping("/vendor")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    /**     * Endpoint to retrieve all vendors.
     * Accessible without authentication.
     *
     * @return a list of VendorBO objects representing all vendors
     */
    @GetMapping(value = "/unsecured/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendorBO> getVendors() {
        return this.vendorService.vendorBOS();
    }

    /**
     * Endpoint to retrieve a vendor by store name.
     * Accessible without authentication.
     *
     * @param storename the store name of the vendor to retrieve
     * @return the VendorBO object representing the vendor with the specified store name
     */
    @GetMapping(value = "/unsecured/{storename}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VendorBO getVendorByStoreName(@PathVariable("storename") String storename) {
        return this.vendorService.findByName(storename);
    }

}
