package com.example.ddd.purchase.service.infrastructure;

import com.example.ddd.purchase.domain.model.PurchaseOrder;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderDto;
import com.example.ddd.purchase.domain.repository.PurchaseOrderRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Collection;
import java.util.Optional;

@Singleton
public class PurchaseOrderJpaRepository implements PurchaseOrderRepository {

    @Inject
    PurchaseOrderEntityRepository purchaseOrderEntityRepository;

    @Override
    public void save(PurchaseOrder purchaseOrder) {
        var state = purchaseOrder.getState();
        purchaseOrderEntityRepository.save(state);
    }

    @Override
    public void saveAll(Collection<PurchaseOrder> purchaseOrders) {

    }

    @Override
    public Optional<PurchaseOrder> findById(String id) {
        var state = purchaseOrderEntityRepository.findById(id);
        var aggregate = PurchaseOrder.createWithState(state.get());
        return Optional.empty();
    }

    @Override
    public void delete(PurchaseOrder purchaseOrder) {

    }

    @Override
    public Optional<PurchaseOrderDto> findDtoById(String id) {
        return Optional.empty();
//        return purchaseOrderEntityRepository.findOneById(id);
    }
}
