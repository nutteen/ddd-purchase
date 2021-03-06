package com.example.ddd.purchase.service.infrastructure;

import com.example.ddd.purchase.domain.model.PurchaseOrder;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderDto;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderLineDto;
import com.example.ddd.purchase.domain.repository.PurchaseOrderRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

@Singleton
public class PurchaseOrderJpaRepository implements PurchaseOrderRepository {

    @Inject
    PurchaseOrderEntityRepository purchaseOrderEntityRepository;
    @Inject
    PurchaseOrderLineEntityRepository purchaseOrderLineEntityRepository;
    @Inject
    EntityManager entityManager;

    @Override
    public void save(PurchaseOrder purchaseOrder) {
        var state = purchaseOrder.getState();
        if(state.getVersion() == null){
            entityManager.persist(state);
        } else {
            entityManager.merge(state);
        }
    }

    @Override
    public void saveAll(Collection<PurchaseOrder> purchaseOrders) {
        for(var purchaseOrder : purchaseOrders){
            var state = purchaseOrder.getState();
            if(state.getVersion() == null){
                entityManager.persist(state);
            } else {
                entityManager.merge(state);
            }
        }
    }

    @Override
    public Optional<PurchaseOrder> findById(String id) {
        var optionalState = purchaseOrderEntityRepository
                .findById(id);
        if(optionalState.isPresent()){
            var state = optionalState.get();
            entityManager.detach(state);
            return Optional.of(PurchaseOrder.createWithState(state));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(PurchaseOrder purchaseOrder) {
        var state = purchaseOrder.getState();
        // delete children
        for(var purchaseOrderLineEntity : state.getOrderLines()){
            purchaseOrderLineEntityRepository.deleteById(purchaseOrderLineEntity.getId());
        }
        // delete aggregate root
        purchaseOrderEntityRepository.deleteById(state.getId());
    }

    @Override
    public Optional<PurchaseOrderDto> findPurchaseOrderDtoById(String id) {
        return purchaseOrderEntityRepository.findOneById(id);
    }

    @Override
    public Slice<PurchaseOrderDto> findPurchaseOrderDtoByCompanyId(String companyId, Pageable pageable) {
        return purchaseOrderEntityRepository.findByCompanyId(companyId, pageable);
    }

    @Override
    public Slice<PurchaseOrderLineDto> findOrderLineSliceByPO(String poId, Pageable pageable) {
        return purchaseOrderLineEntityRepository.findAllByPurchaseOrderId(poId, pageable);
    }
}
