package com.dipakraut.eCommerce.service.order;

import com.dipakraut.eCommerce.enums.OderStatus;
import com.dipakraut.eCommerce.exception.ResourceNotFoundException;
import com.dipakraut.eCommerce.model.Cart;
import com.dipakraut.eCommerce.model.Order;
import com.dipakraut.eCommerce.model.OrderItem;
import com.dipakraut.eCommerce.model.Product;
import com.dipakraut.eCommerce.repository.order.OrderRpository;
import com.dipakraut.eCommerce.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRpository orderRpository;
    private final ProductRepository productRepository;


    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        //set user ....
        order.setOrderStatus(OderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;

    }

    private List<OrderItem> createOrerItems(Order order, Cart cart) {
        return cart.getItems()
                .stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()
                    );
                }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItermList) {
            return orderItermList.stream()
                    .map(item -> item.getPrice()
                            .multiply(new BigDecimal(item.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public Order getOrderById(Long orderId) {
        return orderRpository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order not found"));
    }
}
