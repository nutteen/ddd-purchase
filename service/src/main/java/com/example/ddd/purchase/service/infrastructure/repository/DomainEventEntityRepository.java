package com.example.ddd.purchase.service.infrastructure.repository;

import com.example.ddd.purchase.service.infrastructure.entity.DomainEventEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface DomainEventEntityRepository extends CrudRepository<DomainEventEntity, String> {
}
