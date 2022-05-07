package com.example.ddd.purchase.service.infrastructure;

import com.example.ddd.purchase.domain.model.state.PurchaseOrderLineEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface PurchaseOrderLineEntityRepository extends CrudRepository<PurchaseOrderLineEntity, String> {
}
