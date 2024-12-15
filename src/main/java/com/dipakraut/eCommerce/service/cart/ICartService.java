package com.dipakraut.eCommerce.service.cart;

import com.dipakraut.eCommerce.model.Cart;
import com.dipakraut.eCommerce.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCartById(Long id);
    void clearCart(Long id);
    BigDecimal getCartTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
