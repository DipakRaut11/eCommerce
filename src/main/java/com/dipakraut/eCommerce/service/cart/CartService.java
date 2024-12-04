package com.dipakraut.eCommerce.service.cart;

import com.dipakraut.eCommerce.exception.cart.CartResourcesNotFoundException;
import com.dipakraut.eCommerce.model.Cart;
import com.dipakraut.eCommerce.repository.cart.CartItemRepository;
import com.dipakraut.eCommerce.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCartById(Long id) {
        Cart cart =  cartRepository.findById(id)
                .orElseThrow(() -> new CartResourcesNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCartById(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);


    }

    @Override
    public BigDecimal getCartTotalPrice(Long id) {
        Cart cart = getCartById(id);

//        return cart.getItems()
//                .stream().map(CartItem :: getTotalPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart(){
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return  cartRepository.save(newCart).getId();
    }
}
