package com.sinsuren.order.management.statemachine.api;

/**
 * Created by surender.s on 05/10/17.
 */
public interface StateFullEntity<S, ID> {
    S getStatus();

    void setStatus(S status);

    ID getId();
}
