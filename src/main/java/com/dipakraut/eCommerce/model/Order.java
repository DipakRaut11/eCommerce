package com.dipakraut.eCommerce.model;

import com.dipakraut.eCommerce.enums.OderStatus;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private LocalDate orderDate;
    private Long totalAmount;
    @Enumerated(EnumType.STRING)
    private OderStatus orderStatus;
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true

    )
    private Set<OrderItem> orderItems = new HashSet<>();
}
