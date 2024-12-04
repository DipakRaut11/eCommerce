package com.dipakraut.eCommerce.service.cart;

import com.dipakraut.eCommerce.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCartById(Long id);
    void clearCart(Long id);
    BigDecimal getCartTotalPrice(Long id);

    Long initializeNewCart();
}
