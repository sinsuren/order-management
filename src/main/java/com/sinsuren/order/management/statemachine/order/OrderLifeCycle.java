package com.sinsuren.order.management.statemachine.order;

import com.sinsuren.order.management.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by surender.s on 06/10/17.
 */
@Service
@Slf4j
public class OrderLifeCycle {
    @Autowired
    OrderStateMachineBuilder stateMachineBuilder;

    public Order create(Order order) {
        return stateMachineBuilder.trigger(order, OrderEvent.CREATED, null);
    }

    public Order cancel(Order order) {
        return stateMachineBuilder.trigger(order, OrderEvent.CANCELLED, null);
    }

    public Order pack(Order order) {
        return stateMachineBuilder.trigger(order, OrderEvent.PACKED, null);
    }

    public Order dispatch(Order order) {
        return stateMachineBuilder.trigger(order, OrderEvent.DISPATCHED, null);
    }

    public Order deliver(Order order) {
        return stateMachineBuilder.trigger(order, OrderEvent.DELIVERED, null);
    }
}
