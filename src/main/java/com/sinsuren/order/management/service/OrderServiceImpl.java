package com.sinsuren.order.management.service;

import com.sinsuren.order.management.api.OrderCreationRequest;
import com.sinsuren.order.management.model.Order;
import com.sinsuren.order.management.service.impl.OrderService;
import com.sinsuren.order.management.statemachine.order.OrderLifeCycle;
import com.sinsuren.order.management.statemachine.order.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by surender.s on 05/10/17.
 */
@Service
@Slf4j
@Qualifier("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderLifeCycle orderLifeCycle;

//    @Autowired
//    OrderRepository orderRepository;

    @Override
    public void saveOrder(OrderCreationRequest orderCreationRequest) {
        Order order = Order.builder().customerId(orderCreationRequest.getCustomerId())
                .orderIdentifier(orderCreationRequest.getOrderIdentifier())
                .address(orderCreationRequest.getAddress())
                .name(orderCreationRequest.getName())
                .amount(orderCreationRequest.getAmount())
                .status(OrderStatus.CREATED)
                .build();
        log.info(order.toString());
        orderStateMovement(order);
        log.info(order.toString());
    }

    private void orderStateMovement(Order order) {
        if(order.isNew()) {
            orderLifeCycle.create(order);
        }
    }
}