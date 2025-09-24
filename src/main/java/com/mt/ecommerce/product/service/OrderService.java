package com.mt.ecommerce.product.service;


import com.mt.ecommerce.product.entity.Order;
import com.mt.ecommerce.product.entity.OrderProductEntity;
import com.mt.ecommerce.product.entity.Payment;
import com.mt.ecommerce.product.entity.Product;
import com.mt.ecommerce.product.exception.NoOrderFound;
import com.mt.ecommerce.product.mapper.OrderMapper;
import com.mt.ecommerce.product.model.OrderBO;
import com.mt.ecommerce.product.model.OrderStatus;
import com.mt.ecommerce.product.model.PaymentBO;
import com.mt.ecommerce.product.model.ProductBO;
import com.mt.ecommerce.product.repository.OrderProductRepository;
import com.mt.ecommerce.product.repository.OrderRepository;
import com.mt.ecommerce.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderProductRepository orderProductRepository;

    private final PaymentService paymentService;


    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderProductRepository orderProductRepository, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
        this.paymentService = paymentService;
    }

    @Transactional
    public void addOrder(OrderBO orderBO, UUID vendorId, String userId) {

        Order order = new OrderMapper().mapDAO(orderBO);
        this.orderRepository.save(order);

        AtomicInteger price = new AtomicInteger(0);

        orderBO.getProductBO().forEach(productBO -> {
            Product product = this.productRepository.findById(productBO.getId()).orElseThrow(() -> new IllegalArgumentException("Product is not available" + productBO.getName()));
            if(product.getStockQuantity() - productBO.getQuantity() >= 0){
                product.setStockQuantity(product.getStockQuantity() - productBO.getQuantity());
                this.productRepository.save(product);
                price.set(price.get() + (product.getPrice().intValue() * productBO.getQuantity()));
                OrderProductEntity orderProductEntity = new OrderProductEntity();
                orderProductEntity.setProductId(product);
                orderProductEntity.setOrderId(order);
                orderProductEntity.setQuantity(productBO.getQuantity());
                orderProductRepository.save(orderProductEntity);
            } else {
                throw new IllegalArgumentException("Product is out of stock" + product.getName() +" available stock: " + product.getStockQuantity());
            }
        });
        order.setPrice(price.doubleValue());
        this.orderRepository.save(order);
        PaymentBO  payment = new PaymentBO();
        payment.setAmount(price.doubleValue());
        payment.setOrderId(order.getId());
        payment.setVendorId(vendorId);
        payment.setUserName(userId);
        this.paymentService.createPayment(payment);
    }

    public void editOrderStatus(UUID uuid, OrderStatus status) {
        Order order = this.orderRepository.findById(uuid).orElseThrow(() -> new NoOrderFound("Order not found"));
        order.setOrderStatus(status);
        this.orderRepository.save(order);
    }

    public List<OrderBO> getAllOrder(UUID vendorId, int pageNo, int size) {
        return this.orderRepository.findByVendorId(vendorId, PageRequest.of(pageNo, size)).stream().map(order -> new OrderMapper().mapBO(order, this.orderProductRepository.findAllByOrderId(order))).collect(Collectors.toList());
    }

    public List<OrderBO> getAllOrderByUserId(String userId, int pageNo, int size) {
        return this.orderRepository.findByUserId(userId, PageRequest.of(pageNo, size)).stream().map(order -> new OrderMapper().mapBO(order, this.orderProductRepository.findAllByOrderId(order))).collect(Collectors.toList());
    }


}
