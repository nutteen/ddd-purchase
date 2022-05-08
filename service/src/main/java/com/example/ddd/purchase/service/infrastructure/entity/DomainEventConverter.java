package com.example.ddd.purchase.service.infrastructure.entity;

import com.example.ddd.purchase.domain.common.DomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Singleton;

@Singleton
public class DomainEventConverter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DomainEventEntity toEntity(DomainEvent domainEvent) {
        try {
            var domainEventEntity = new DomainEventEntity();
            domainEventEntity.setId(domainEvent.getEventId());
            domainEventEntity.setType(domainEvent.getClass().getName());
            domainEventEntity.setSerializedEvent(objectMapper.writeValueAsString(domainEvent));
            return domainEventEntity;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to serialized domain event", e);
        }
    }

    public DomainEvent fromEntity(DomainEventEntity domainEventEntity) {
        try {
            var type = domainEventEntity.getType();
            var clazz = Class.forName(type);
            var serializedEvent = domainEventEntity.getSerializedEvent();
            return (DomainEvent) objectMapper.readValue(serializedEvent, clazz);
        } catch (ClassNotFoundException | JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to deserialized domain event", e);
        }
    }
}
