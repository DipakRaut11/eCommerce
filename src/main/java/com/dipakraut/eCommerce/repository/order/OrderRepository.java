package com.dipakraut.eCommerce.repository.order;

import com.dipakraut.eCommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRpository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
}
