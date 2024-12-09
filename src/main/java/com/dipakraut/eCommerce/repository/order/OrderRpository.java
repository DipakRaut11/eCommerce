package com.dipakraut.eCommerce.repository.order;

import com.dipakraut.eCommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRpository extends JpaRepository<Order, Long> {
}
