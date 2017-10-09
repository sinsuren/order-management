package com.sinsuren.order.management.statemachine.order;

import com.sinsuren.order.management.core.statemachine.BasicStateMachine;
import com.sinsuren.order.management.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.annotation.Transit;
import org.squirrelframework.foundation.fsm.annotation.Transitions;

/**
 * Created by surender.s on 06/10/17.
 */

//CREATED, CANCELLED, PACKED, DISPATCHED, DELIVERED

@Transitions({
        @Transit(from = "CREATED", to = "CREATED", on = "CREATED"),
        @Transit(from = "CREATED", to = "CANCELLED", on = "CANCELLED"),
        @Transit(from = "CREATED", to = "PACKED", on = "PACKED"),

        @Transit(from = "PACKED", to = "CANCELLED", on = "CANCELLED", callMethod = "cancelled"),
        @Transit(from = "PACKED", to = "DISPATCHED", on = "DISPATCHED", callMethod = "dispatched"),
        @Transit(from = "DISPATCHED", to = "DELIVERED", on = "DELIVERED", callMethod = "delivered")

})
@Slf4j
public class OrderStateMachineType extends BasicStateMachine<OrderStateMachineType, OrderStatus, OrderEvent, OrderContext, Order, OrderTransitionHandler> {

    public OrderStateMachineType(Order entity, OrderTransitionHandler transitionCompleteHook, ApplicationContext applicationContext) {
        super(entity, transitionCompleteHook, applicationContext);
    }

    public void cancelled(OrderStatus from, OrderStatus to, OrderEvent on, OrderContext context) {
        entity.setStatus(OrderStatus.CANCELLED);
    }

    public void packed(OrderStatus from, OrderStatus to, OrderEvent on, OrderContext context) {
        entity.setStatus(OrderStatus.PACKED);
    }

    public void delivered(OrderStatus from, OrderStatus to, OrderEvent on, OrderContext context) {
        entity.setStatus(OrderStatus.DELIVERED);
    }

    public void dispatched(OrderStatus from, OrderStatus to, OrderEvent on, OrderContext context) {
        entity.setStatus(OrderStatus.DISPATCHED);
    }

    public void created(OrderStatus from, OrderStatus to, OrderEvent on, OrderContext context) {
        entity.setStatus(OrderStatus.CREATED);
    }
}
