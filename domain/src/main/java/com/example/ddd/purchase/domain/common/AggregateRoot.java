package com.example.ddd.purchase.domain.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class that would be extended by aggregate root type
 * @param <I> Id type of an aggregate
 * @param <S> State type of an aggregate for persistence
 */
public abstract class AggregateRoot<I, S> {
    private final Set<DomainEvent> domainEvents = new HashSet<>();
    public abstract I getId();
    public abstract S getState();

    /**
     * internal method for aggregate root to register domain event
     * @param domainEvent domain event generated from aggregate
     */
    protected void registerDomainEvent(DomainEvent domainEvent){
        domainEvents.add(domainEvent);
    }

    /**
     * Interface for infrastructure to get events and publish
     * @return domain event
     */
    public Collection<DomainEvent> getDomainEvents(){
        return domainEvents;
    }
}
