package com.sinsuren.order.management.resource;

import com.codahale.metrics.annotation.Timed;
import com.sinsuren.order.management.api.OrderCreationRequest;
import com.sinsuren.order.management.core.util.TransactionalWithRollback;
import com.sinsuren.order.management.service.impl.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by surender.s on 05/10/17.
 */
@Slf4j
@RestController
@Component
@RequestMapping(value = "/order")
public class OrderController {

    @Qualifier("orderService")
    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    @TransactionalWithRollback
    @Timed
    public ResponseEntity<?> saveOrder(@RequestBody OrderCreationRequest orderCreationRequest) {
        orderService.saveOrder(orderCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
