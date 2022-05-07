package com.example.ddd.purchase.service.infrastructure;

import com.example.ddd.purchase.domain.model.query.PurchaseOrderLineDto;
import com.example.ddd.purchase.domain.model.state.PurchaseOrderLineEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface PurchaseOrderLineEntityRepository extends CrudRepository<PurchaseOrderLineEntity, Integer> {
    Slice<PurchaseOrderLineDto> findByPoId(String poId, Pageable pageable);
}
