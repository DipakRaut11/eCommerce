package com.dipakraut.eCommerce.repository.cart;
import com.dipakraut.eCommerce.model.Cart;
import com.dipakraut.eCommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteAllByCart(Long id);
}
