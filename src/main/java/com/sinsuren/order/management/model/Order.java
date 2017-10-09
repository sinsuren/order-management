package com.sinsuren.order.management.model;

import com.sinsuren.order.management.statemachine.api.StateFullEntity;
import com.sinsuren.order.management.statemachine.order.OrderStatus;
import com.sinsuren.order.management.util.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by surender.s on 04/10/17.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order extends BasicEntity implements StateFullEntity<OrderStatus, Long> {

    @Id
    @Column(name = "id")
    private Long Id;

    @Column(name = "orderIdentifier")
    private String orderIdentifier;

    @Column(name = "address")
    private String address;

    @Column(name = "customerId")
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OrderStatus status;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Double amount;
}
