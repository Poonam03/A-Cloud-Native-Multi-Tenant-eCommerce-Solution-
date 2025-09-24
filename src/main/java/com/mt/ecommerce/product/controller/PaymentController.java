package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.model.PaymentBO;
import com.mt.ecommerce.product.service.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PaymentBO> getVendorOrder(
            @RequestParam(name = "vendorId") String vendorID) {
        return this.paymentService.getPayment(java.util.UUID.fromString(vendorID));
    }
}
