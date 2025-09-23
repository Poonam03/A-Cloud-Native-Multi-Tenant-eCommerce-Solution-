package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.model.PaymentBO;
import com.mt.ecommerce.product.service.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentBO getVendorOrder(
            @RequestParam(name = "orderID") String orderID) {
        return this.paymentService.getPayment(java.util.UUID.fromString(orderID));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addOrder(PaymentBO paymentBO) {
        this.paymentService.createPayment(paymentBO);
    }
}
