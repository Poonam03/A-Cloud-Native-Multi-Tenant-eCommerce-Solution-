package com.mt.ecommerce.product.service;


import com.mt.ecommerce.product.entity.Order;
import com.mt.ecommerce.product.exception.NoOrderFound;
import com.mt.ecommerce.product.mapper.OrderMapper;
import com.mt.ecommerce.product.model.OrderBO;
import com.mt.ecommerce.product.model.OrderStatus;
import com.mt.ecommerce.product.model.ProductBO;
import com.mt.ecommerce.product.repository.OrderRepository;
import com.mt.ecommerce.product.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;


    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void addOrder(OrderBO orderBO) {
        this.orderRepository.save(new OrderMapper().mapDAO(orderBO));
    }

    public void editOrderStatus(UUID uuid, OrderStatus status) {
        Order order = this.orderRepository.findById(uuid).orElseThrow(() -> new NoOrderFound("Order not found"));
        order.setOrderStatus(status);
        this.orderRepository.save(order);
    }

    public List<OrderBO> getAllOrder(UUID vendorId, int pageNo, int size) {
        return this.orderRepository.findByVendorID(vendorId, PageRequest.of(pageNo, size)).stream().map(order -> {
            List<ProductBO> product = this.productRepository.findAllById(order.getProductId())
                    .stream()
                    .map(product1 -> {
                        ProductBO productBO = new ProductBO();
                        productBO.setId(product1.getId());
                        productBO.setName(product1.getName());
                        productBO.setPrice(product1.getPrice());
                        return productBO;
                    })
                    .collect(Collectors.toList());
            return new OrderMapper().mapBO(order, product);
        }).collect(Collectors.toList());
    }

    public List<OrderBO> getAllOrderByUserId(String userId, int pageNo, int size) {
        return this.orderRepository.findByUserID(userId, PageRequest.of(pageNo, size)).stream().map(order -> {
            List<ProductBO> product = this.productRepository.findAllById(order.getProductId())
                    .stream()
                    .map(product1 -> {
                        ProductBO productBO = new ProductBO();
                        productBO.setId(product1.getId());
                        productBO.setName(product1.getName());
                        productBO.setPrice(product1.getPrice());
                        return productBO;
                    })
                    .collect(Collectors.toList());
            return new OrderMapper().mapBO(order, product);
        }).collect(Collectors.toList());
    }


}
