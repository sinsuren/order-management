package com.sinsuren.order.management.statemachine.order;

import com.sinsuren.order.management.core.statemachine.AbstractStateMachineBuilder;
import com.sinsuren.order.management.model.Order;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;

/**
 * Created by surender.s on 06/10/17.
 */
@Component
public class OrderStateMachineBuilder extends AbstractStateMachineBuilder<OrderStateMachineType, OrderStatus, OrderEvent, OrderContext, Order, OrderTransitionHandler> {

    @Override
    protected void addMultiFromStateTransition(StateMachineBuilder<OrderStateMachineType, OrderStatus, OrderEvent, OrderContext> stateMachineBuilder) {
        stateMachineBuilder.transitions()
                .fromAmong(ArrayUtils.removeElement(OrderStatus.values(), OrderStatus.CANCELLED))
                .to(OrderStatus.CANCELLED)
                .on(OrderEvent.CANCELLED)
                .callMethod("cancelled");
    }

    @Override
    @Autowired
    public void setTransitionCompleteHook(OrderTransitionHandler transitionCompleteHook) {
        this.transitionCompleteHook = transitionCompleteHook;
    }

    @Override
    protected StateMachineBuilder<OrderStateMachineType, OrderStatus, OrderEvent, OrderContext> createBasicStateMachine() {
        return StateMachineBuilderFactory.create(OrderStateMachineType.class,
                OrderStatus.class, OrderEvent.class, OrderContext.class,
                Order.class, OrderTransitionHandler.class, ApplicationContext.class);
    }
}
