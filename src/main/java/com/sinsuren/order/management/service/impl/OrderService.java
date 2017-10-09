package com.sinsuren.order.management.service.impl;

import com.sinsuren.order.management.api.OrderCreationRequest;

/**
 * Created by surender.s on 05/10/17.
 */
public interface OrderService {
    void saveOrder(OrderCreationRequest orderCreationRequest);
}
