package com.dipakraut.eCommerce.dto.order;

import com.dipakraut.eCommerce.enums.OderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OderStatus orderStatus;
    private Set<OrderItemDto> items;
}
