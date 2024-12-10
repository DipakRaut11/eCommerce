package com.dipakraut.eCommerce.repository.cart;

import com.dipakraut.eCommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
