package com.sinsuren.order.management.statemachine.order;

import com.sinsuren.order.management.core.statemachine.api.TransitionCompleteHook;
import com.sinsuren.order.management.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by surender.s on 06/10/17.
 */
@Component
@Slf4j
public class OrderTransitionHandler implements TransitionCompleteHook<OrderStatus, OrderEvent, OrderContext, Order> {
    //Few Generic function which are supposed to happen in each and every state change

    @Override
    public void setState(OrderStatus from, OrderStatus to, OrderEvent on, OrderContext context, Order order) {
        order.setStatus(to);
    }
}
