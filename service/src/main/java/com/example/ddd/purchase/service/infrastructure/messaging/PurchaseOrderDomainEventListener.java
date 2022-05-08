package com.example.ddd.purchase.service.infrastructure.messaging;

import com.example.ddd.purchase.domain.api.event.PurchaseOrderCreatedEvent;
import io.micronaut.transaction.annotation.TransactionalEventListener;
import jakarta.inject.Singleton;

@Singleton
public class PurchaseOrderDomainEventListener {
    @TransactionalEventListener
    public void on(PurchaseOrderCreatedEvent purchaseOrderCreatedEvent){
        // TODO: forward the event to external band
    }
}
