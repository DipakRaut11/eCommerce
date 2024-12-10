package com.dipakraut.eCommerce.service.order;

import com.dipakraut.eCommerce.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    Order getOrderById(Long orderId);

    List<Order> getUserOrder(Long userId);
}
