package com.dipakraut.eCommerce.dto.cart;

import com.dipakraut.eCommerce.dto.product.ProductDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long ItemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
