package com.mt.ecommerce.product.mapper;

import com.mt.ecommerce.product.entity.Order;
import com.mt.ecommerce.product.entity.Product;
import com.mt.ecommerce.product.model.OrderBO;
import com.mt.ecommerce.product.model.ProductBO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public Order mapDAO(OrderBO orderBO) {
        Order order = new Order();
        order.setId(orderBO.getId());
        order.setUserId(orderBO.getUserInfoBO().getEmail());
        order.setProductId(orderBO.getProductBO().stream().map(ProductBO::getId).collect(Collectors.toList()));
        order.setCity(orderBO.getCity());
        order.setOrderStatus(orderBO.getOrderStatus());
        order.setAddressLine1(orderBO.getAddressLine1());
        order.setAddressLine2(orderBO.getAddressLine2());
        order.setCountry(orderBO.getCountry());
        order.setPhone(orderBO.getPhone());
        order.setState(orderBO.getState());
        order.setVendorId(orderBO.getVendorId());
        return order;
    }

    public OrderBO mapBO(Order order, List<ProductBO> productBO){
        OrderBO orderBO = new OrderBO();
        orderBO.setId(order.getId());
        orderBO.setCity(order.getCity());
        orderBO.setOrderStatus(order.getOrderStatus());
        orderBO.setAddressLine1(order.getAddressLine1());
        orderBO.setAddressLine2(order.getAddressLine2());
        orderBO.setCountry(order.getCountry());
        orderBO.setPhone(order.getPhone());
        orderBO.setState(order.getState());
        orderBO.setVendorId(order.getVendorId());
        orderBO.setProductBO(productBO);
        return orderBO;
    }

}
