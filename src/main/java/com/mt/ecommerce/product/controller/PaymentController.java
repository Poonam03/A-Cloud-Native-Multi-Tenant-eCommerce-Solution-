package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.model.PaymentBO;
import com.mt.ecommerce.product.service.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing payments.
 * Provides endpoints for retrieving payment information.
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Endpoint to retrieve payments for a specific vendor.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param vendorID the ID of the vendor whose payments are to be retrieved
     * @return a list of PaymentBO objects representing the vendor's payments
     */
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PaymentBO> getVendorOrder(
            @RequestParam(name = "vendorId") String vendorID) {
        return this.paymentService.getPayment(java.util.UUID.fromString(vendorID));
    }
}
