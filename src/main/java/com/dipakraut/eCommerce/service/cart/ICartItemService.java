package com.dipakraut.eCommerce.service.cart;

import com.dipakraut.eCommerce.model.CartItem;

public interface ICartItemService {

    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateCartItemQuantity(Long cartId, Long productId, int quantity);

}
