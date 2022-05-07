package com.example.ddd.purchase.domain.repository;

import com.example.ddd.purchase.domain.model.PurchaseOrder;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderDto;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderLineDto;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;

import java.util.Collection;
import java.util.Optional;

public interface PurchaseOrderRepository {
    // aggregate operations
    void save(PurchaseOrder purchaseOrder);
    void saveAll(Collection<PurchaseOrder> purchaseOrders);
    Optional<PurchaseOrder> findById(String id);
    void delete(PurchaseOrder purchaseOrder);

    // projections
    Optional<PurchaseOrderDto> findPurchaseOrderDtoById(String id);
    Slice<PurchaseOrderDto> findPurchaseOrderDtoByCompanyId(String companyId, Pageable pageable);
    Slice<PurchaseOrderLineDto> findOrderLineSliceByPO(String poId, Pageable pageable);
}
