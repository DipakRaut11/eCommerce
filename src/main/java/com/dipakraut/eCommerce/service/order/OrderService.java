package com.dipakraut.eCommerce.service.order;

import com.dipakraut.eCommerce.enums.OderStatus;
import com.dipakraut.eCommerce.exception.ResourceNotFoundException;
import com.dipakraut.eCommerce.model.Cart;
import com.dipakraut.eCommerce.model.Order;
import com.dipakraut.eCommerce.model.OrderItem;
import com.dipakraut.eCommerce.model.Product;
import com.dipakraut.eCommerce.repository.order.OrderRpository;
import com.dipakraut.eCommerce.repository.product.ProductRepository;
import com.dipakraut.eCommerce.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRpository orderRpository;
    private final ProductRepository productRepository;
    private final CartService cartService;


    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrerItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order saveOrder = orderRpository.save(order);
        cartService.clearCart(cart.getId());

        return saveOrder;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
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

    @Override
    public List<Order> getUserOrder(Long userId) {
        return orderRpository.findByUserId(userId);
    }
}
