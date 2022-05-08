package com.example.ddd.purchase.service.infrastructure.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "domain_event")
public class DomainEventEntity {
    @Id
    private String id;
    private String type;
    private String serializedEvent;

    public DomainEventEntity(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerializedEvent() {
        return serializedEvent;
    }

    public void setSerializedEvent(String serializedEvent) {
        this.serializedEvent = serializedEvent;
    }
}
