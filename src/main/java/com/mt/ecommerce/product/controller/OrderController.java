package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.model.OrderBO;
import com.mt.ecommerce.product.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for managing orders.
 * Provides endpoints for adding, updating, and retrieving orders.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint to retrieve orders for a specific vendor.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param vendorId the ID of the vendor whose orders are to be retrieved
     * @param pageNo   the page number for pagination (default is 0)
     * @param size     the number of records per page (default is 10)
     * @return a list of OrderBO objects representing the vendor's orders
     */
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @GetMapping(value = "/vendor", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderBO> getVendorOrder(
            @RequestParam(name = "vendorId") String vendorId,
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return this.orderService.getAllOrder(java.util.UUID.fromString(vendorId), pageNo, size);
    }

    /**
     * Endpoint to retrieve orders for the authenticated user.
     * Accessible by users with ROLE_USER.
     *
     * @param userDetails the authenticated user's details
     * @param pageNo      the page number for pagination (default is 0)
     * @param size        the number of records per page (default is 10)
     * @return a list of OrderBO objects representing the user's orders
     */
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderBO> getUserOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return this.orderService.getAllOrderByUserId(userDetails.getUsername(), pageNo, size);
    }

    /**
     * Endpoint to update the status of an order.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param orderId the ID of the order to be updated
     * @param status  the new status for the order
     */
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    @PutMapping(value = "" )
    public void updateOrderStatus(
            @RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "status") String status) {
        this.orderService.editOrderStatus(java.util.UUID.fromString(orderId), com.mt.ecommerce.product.model.OrderStatus.valueOf(status));
    }

    /**
     * Endpoint to add a new order.
     * Accessible by users with ROLE_USER or ROLE_VENDOR.
     *
     * @param vendorID    the ID of the vendor for whom the order is being placed
     * @param orderBO     the order details
     * @param userDetails the authenticated user's details
     */
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_VENDOR')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addOrder(@RequestParam(name = "vendor") UUID vendorID, @RequestBody  OrderBO orderBO, @AuthenticationPrincipal UserDetails userDetails){
        this.orderService.addOrder(orderBO, vendorID, userDetails.getUsername());
    }
}
