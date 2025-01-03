package com.dipakraut.eCommerce.service.cart;

import com.dipakraut.eCommerce.exception.ResourceNotFoundException;
import com.dipakraut.eCommerce.model.Cart;
import com.dipakraut.eCommerce.model.User;
import com.dipakraut.eCommerce.repository.cart.CartItemRepository;
import com.dipakraut.eCommerce.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
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
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCartById(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        //cart.clearCart();
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
    public Cart initializeNewCart(User user){
       return Optional.ofNullable(getCartByUserId(user.getId()))
               .orElseGet(() -> {
                   Cart cart = new Cart();
                   cart.setUser(user);
                   return cartRepository.save(cart);
               });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
