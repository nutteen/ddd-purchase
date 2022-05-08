package com.example.ddd.purchase.service.infrastructure.repository;

import com.example.ddd.purchase.domain.model.query.PurchaseOrderLineDto;
import com.example.ddd.purchase.domain.model.state.PurchaseOrderLineEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface PurchaseOrderLineEntityRepository extends CrudRepository<PurchaseOrderLineEntity, String> {
    @Query("select new com.example.ddd.purchase.domain.model.query.PurchaseOrderLineDto (e.id, e.lineOrder, e.unit, e.unitPrice, e.partId) FROM PurchaseOrderLineEntity e WHERE e.purchaseOrder.id = :purchaseOrderId")
    Slice<PurchaseOrderLineDto> findAllByPurchaseOrderId(String purchaseOrderId, Pageable pageable);
}
