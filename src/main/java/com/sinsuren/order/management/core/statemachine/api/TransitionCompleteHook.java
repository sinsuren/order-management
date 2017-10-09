package com.sinsuren.order.management.core.statemachine.api;

import com.sinsuren.order.management.statemachine.api.StateFullEntity;

/**
 * Created by surender.s on 06/10/17.
 */
public interface TransitionCompleteHook<S, E, C, Entity extends StateFullEntity<S, ?>> {
    //Few generic functions which you want to do in each transaction
    default void setState(S from, S to, E on, C context, Entity entity) {
        entity.setStatus(to);
    }
}
