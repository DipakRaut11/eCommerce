package com.dipakraut.eCommerce.service.order;

import com.dipakraut.eCommerce.dto.order.OrderDto;
import com.dipakraut.eCommerce.enums.OderStatus;
import com.dipakraut.eCommerce.exception.ResourceNotFoundException;
import com.dipakraut.eCommerce.model.Cart;
import com.dipakraut.eCommerce.model.Order;
import com.dipakraut.eCommerce.model.OrderItem;
import com.dipakraut.eCommerce.model.Product;
import com.dipakraut.eCommerce.repository.order.OrderRepository;
import com.dipakraut.eCommerce.repository.product.ProductRepository;
import com.dipakraut.eCommerce.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRpository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;


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
    public OrderDto getOrderById(Long orderId) {
        return orderRpository.findById(orderId)
                .map(this :: convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRpository.findByUserId(userId);
        return orders.stream()
                .map(this :: convertToDto)
                .toList();
    }


    


    @Override
    public OrderDto convertToDto(Order order){
        return modelMapper.map(order, OrderDto.class);
    }
}
