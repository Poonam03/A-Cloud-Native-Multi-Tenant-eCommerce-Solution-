package com.mt.ecommerce.product.mapper;

import com.mt.ecommerce.product.entity.Order;
import com.mt.ecommerce.product.entity.OrderProductEntity;
import com.mt.ecommerce.product.entity.Product;
import com.mt.ecommerce.product.model.OrderBO;
import com.mt.ecommerce.product.model.ProductBO;
import com.mt.ecommerce.product.repository.ProductRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/** * Mapper class for converting between Order entity and OrderBO model.
 */
public class OrderMapper {

    /**     * Maps an OrderBO object to an Order entity.
     *
     * @param orderBO the OrderBO object to be mapped
     * @return the mapped Order entity
     */
    public Order mapDAO(OrderBO orderBO) {
        Order order = new Order();
        order.setId(orderBO.getId());
        order.setUserId(orderBO.getUserInfoBO().getEmail());
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

    /**     * Maps an Order entity and its associated OrderProductEntities to an OrderBO object.
     *
     * @param order                the Order entity to be mapped
     * @param orderProductEntities the list of associated OrderProductEntities
     * @param productRepository    the ProductRepository for fetching product details
     * @return the mapped OrderBO object
     */
    public OrderBO mapBO(Order order, List<OrderProductEntity> orderProductEntities, ProductRepository productRepository){
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
        List<ProductBO> productBO = orderProductEntities.stream().map(orderProductEntity -> {
            Product product = productRepository.findById(orderProductEntity.getProductId()).orElse(null);
            if(product == null){
                return null;
            }
            ProductBO productBO1 = new ProductBO();
            productBO1.setName(product.getName());
            productBO1.setDescription(product.getDescription());
            productBO1.setPrice(product.getPrice());
            productBO1.setQuantity(orderProductEntity.getQuantity());
            return productBO1;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        orderBO.setProductBO(productBO);
        orderBO.setPrice(order.getPrice());
        orderBO.setOrderDate(order.getCreatedAt());
        return orderBO;
    }

}
