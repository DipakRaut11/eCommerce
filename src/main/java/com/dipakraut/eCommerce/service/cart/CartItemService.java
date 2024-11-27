package com.dipakraut.eCommerce.service.cart;

import com.dipakraut.eCommerce.model.Cart;
import com.dipakraut.eCommerce.model.CartItem;
import com.dipakraut.eCommerce.model.Product;
import com.dipakraut.eCommerce.repository.cart.CartItemRepository;
import com.dipakraut.eCommerce.repository.cart.CartRepository;
import com.dipakraut.eCommerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // get the cart
        // get the product
        //check if the product already in the cart
        // if yes then increase the quantity with the requested quentity
        //if no, then initiate a new cartItem entry

        Cart cart = cartService.getCartById(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());


        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);




    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {

    }

    @Override
    public void updateCartItemQuantity(Long cartId, Long productId, int quantity) {

    }
}
