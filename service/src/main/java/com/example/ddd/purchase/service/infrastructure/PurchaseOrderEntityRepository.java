package com.example.ddd.purchase.service.infrastructure;

import com.example.ddd.purchase.domain.model.query.PurchaseOrderDto;
import com.example.ddd.purchase.domain.model.state.PurchaseOrderEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface PurchaseOrderEntityRepository extends CrudRepository<PurchaseOrderEntity, String> {
    Optional<PurchaseOrderDto> findOneById(String id);
}
