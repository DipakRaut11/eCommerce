package com.dipakraut.eCommerce.service.order;

import com.dipakraut.eCommerce.model.Order;

public interface IOrderService {

    Order placeOrder(Long userId);
    Order getOrderById(Long orderId);
}
