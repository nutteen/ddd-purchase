package com.example.ddd.purchase.domain.repository;

import com.example.ddd.purchase.domain.model.PurchaseOrder;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderDto;

import java.util.Collection;
import java.util.Optional;

public interface PurchaseOrderRepository {
    void save(PurchaseOrder purchaseOrder);
    void saveAll(Collection<PurchaseOrder> purchaseOrders);
    Optional<PurchaseOrder> findById(String id);
    void delete(PurchaseOrder purchaseOrder);
    Optional<PurchaseOrderDto> findDtoById(String id);
}
