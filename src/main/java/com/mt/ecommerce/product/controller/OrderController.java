package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.model.OrderBO;
import com.mt.ecommerce.product.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @GetMapping(value = "/vendor", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderBO> getVendorOrder(
            @RequestParam(name = "vendorId") String vendorId,
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return this.orderService.getAllOrder(java.util.UUID.fromString(vendorId), pageNo, size);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderBO> getUserOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return this.orderService.getAllOrderByUserId(userDetails.getUsername(), pageNo, size);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @PutMapping(value = "" )
    public void updateOrderStatus(
            @RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "status") String status) {
        this.orderService.editOrderStatus(java.util.UUID.fromString(orderId), com.mt.ecommerce.product.model.OrderStatus.valueOf(status));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addOrder(OrderBO orderBO){
        this.orderService.addOrder(orderBO);
    }
}
