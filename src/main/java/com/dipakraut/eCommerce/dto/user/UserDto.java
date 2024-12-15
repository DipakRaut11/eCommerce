package com.dipakraut.eCommerce.dto.user;

import com.dipakraut.eCommerce.dto.cart.CartDto;
import com.dipakraut.eCommerce.dto.order.OrderDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders = new ArrayList<>();
    private CartDto cart;
}
