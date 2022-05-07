package com.example.ddd.purchase.service.infrastructure;

import com.example.ddd.purchase.domain.model.PurchaseOrder;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderDto;
import com.example.ddd.purchase.domain.repository.PurchaseOrderRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class PurchaseOrderJpaRepository implements PurchaseOrderRepository {

    @Inject
    PurchaseOrderEntityRepository purchaseOrderEntityRepository;
    @Inject
    PurchaseOrderLineEntityRepository purchaseOrderLineEntityRepository;

    @Override
    public void save(PurchaseOrder purchaseOrder) {
        purchaseOrderEntityRepository.save(purchaseOrder.getState());
    }

    @Override
    public void saveAll(Collection<PurchaseOrder> purchaseOrders) {
        var purchaseOrderStates = purchaseOrders
                .stream()
                .map(PurchaseOrder::getState);
        purchaseOrderEntityRepository.saveAll(purchaseOrderStates.collect(Collectors.toList()));
    }

    @Override
    public Optional<PurchaseOrder> findById(String id) {
        return purchaseOrderEntityRepository
                .findById(id)
                .map(PurchaseOrder::createWithState);
    }

    @Override
    public void delete(PurchaseOrder purchaseOrder) {
        // delete child
        for(var orderLine : purchaseOrder.getOrderLines()){
            purchaseOrderLineEntityRepository.deleteById(orderLine.getId());
        }
        // delete aggregate root
        purchaseOrderEntityRepository.deleteById(purchaseOrder.getId());
    }

    @Override
    public Optional<PurchaseOrderDto> findPurchaseOrderDtoById(String id) {
        return purchaseOrderEntityRepository.findOneById(id);
    }

    @Override
    public Slice<PurchaseOrderDto> findPurchaseOrderDtoByCompanyId(String companyId, Pageable pageable) {
        return purchaseOrderEntityRepository.findByCompanyId(companyId, pageable);
    }
}
