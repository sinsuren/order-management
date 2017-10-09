package com.sinsuren.order.management.core.statemachine;

import com.sinsuren.order.management.core.statemachine.api.TransitionCompleteHook;
import com.sinsuren.order.management.statemachine.api.StateFullEntity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by surender.s on 06/10/17.
 */
@Slf4j
public abstract class AbstractStateMachineBuilder<T extends BasicStateMachine<T, S, E, C, Entity, I>, S, E, C,
        Entity extends StateFullEntity<S, ?>,
        I extends TransitionCompleteHook<S, E, C, Entity>>
        implements InitializingBean {
    @Getter
    @Setter
    public StateMachineBuilder<T, S, E, C> stateMachineBuilder;

    public I transitionCompleteHook;

    @Autowired
    ApplicationContext applicationContext;

    public Entity trigger(@NonNull Entity entity, @NonNull E on, C context) {
        final T stateMachine = createNewStateMachine(entity);
        stateMachine.fire(on, context);
        return entity;
    }

    public List<Entity> trigger(@NonNull List<Entity> entities, @NonNull E on, @NonNull C context, @NonNull Function<List<Entity>, Boolean> verifier) {
        // Word of advice can throw exception in verifier to break execution flow
        final Boolean apply = verifier.apply(entities);
        if (apply)
            return trigger(entities, on, context);
        else
            log.info("Verifier failed not doing state transition for the list");
        return entities;
    }

    public List<Entity> trigger(@NonNull List<Entity> entities, @NonNull E on, @NonNull C context) {
        return entities.stream().map(entity -> trigger(entity, on, context)).collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        stateMachineBuilder = createBasicStateMachine();
        Assert.notNull(stateMachineBuilder, "StateMachine Need to be Iniitialized");
        Assert.notNull(transitionCompleteHook, "TransitionCompleteHook need to be initialized");
        addMultiFromStateTransition(stateMachineBuilder);
    }

    protected T createNewStateMachine(Entity entity) {
        return stateMachineBuilder.newStateMachine(entity.getStatus(), entity, transitionCompleteHook, applicationContext);
    }

    protected abstract void addMultiFromStateTransition(StateMachineBuilder<T, S, E, C> stateMachineBuilder);


    protected abstract StateMachineBuilder<T, S, E, C> createBasicStateMachine();

    public abstract void setTransitionCompleteHook(I transitionCompleteHook);
}
