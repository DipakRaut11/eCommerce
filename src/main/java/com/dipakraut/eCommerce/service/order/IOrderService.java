package com.dipakraut.eCommerce.service.order;

import com.dipakraut.eCommerce.dto.order.OrderDto;
import com.dipakraut.eCommerce.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    OrderDto getOrderById(Long orderId);

    List<OrderDto> getUserOrders(Long userId);


    OrderDto convertToDto(Order order);
}
