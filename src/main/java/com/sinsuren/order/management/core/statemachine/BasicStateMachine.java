package com.sinsuren.order.management.core.statemachine;

import com.sinsuren.order.management.core.statemachine.api.TransitionCompleteHook;
import com.sinsuren.order.management.statemachine.api.StateFullEntity;
import com.sinsuren.order.management.util.exception.ClientException;
import com.sinsuren.order.management.util.exception.UnprocessableEntityException;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

/**
 * Created by surender.s on 06/10/17.
 */
@Slf4j
public abstract class BasicStateMachine<T extends AbstractStateMachine<T, S, E, C>, S, E, C,
        Entity extends StateFullEntity<S, ?>,
        I extends TransitionCompleteHook<S, E, C, Entity>> extends AbstractStateMachine<T, S, E, C> {
    @Getter
    @NonNull
    public final Entity entity;
    @NonNull
    public final I transitionCompleteHook;
    @NonNull
    public final ApplicationContext applicationContext;

    public BasicStateMachine(Entity entity, I transitionCompleteHook, ApplicationContext applicationContext) {
        this.entity = entity;
        this.applicationContext = applicationContext;
        this.transitionCompleteHook = transitionCompleteHook;
    }

    @Override
    protected void afterTransitionDeclined(S from, E event, C context) {
        throw new UnprocessableEntityException(String.format("Can't do state transition of %s from %s on %s",
                getEntityString(entity), from, event));
    }

    @Override
    @SneakyThrows
    protected void afterTransitionCausedException(S from, S to, E on, C context) {
        final Throwable targetException = getLastException().getTargetException();
        if(targetException != null) {
            handleTargetException(from, to, on, targetException);
        }
        throw getLastException();
    }

    @Override
    protected void beforeTransitionBegin(S from, E on, C context) {
        log.info("Trying transition of {} from {} on {}", getEntityString(entity), from, on);
    }

    @Override
    protected void afterTransitionEnd(S from, S to, E on, C context) {
        log.info("Successfully transitioned {} from {} to {} on {}", getEntityString(entity), from, to, on);
    }

    @Override
    protected void afterTransitionCompleted(S from, S to, E on, C context) {
        transitionCompleteHook.setState(from, to, on, context, entity);
    }

    private void handleTargetException(S from, S to, E on, Throwable targetException) throws Throwable {
        final String message = String.format("Some Exception happened during transition of %s from %s to %s on %s",
                getEntityString(entity), from, to, on);
        log.error(message, targetException);
        if(targetException instanceof ClientException) {
            throw targetException;
        }
    }


    private String getEntityString(Entity entity) {
        return String.format("%s with Id: %s", entity.getClass().getName(), entity.getId());
    }
}
