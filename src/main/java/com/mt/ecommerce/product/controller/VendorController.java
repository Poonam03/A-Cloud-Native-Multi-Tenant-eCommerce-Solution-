package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.model.VendorBO;
import com.mt.ecommerce.product.service.VendorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @GetMapping(value = "/unsecured/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendorBO> getVendors() {
        return this.vendorService.vendorBOS();
    }

    @GetMapping(value = "/unsecured/{storename}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VendorBO getVendorByStoreName(@PathVariable("storename") String storename) {
        return this.vendorService.findByName(storename);
    }

}
