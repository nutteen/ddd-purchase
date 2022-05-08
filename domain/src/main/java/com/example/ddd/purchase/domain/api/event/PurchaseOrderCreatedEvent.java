package com.example.ddd.purchase.domain.api.event;

import com.example.ddd.purchase.domain.common.DomainEvent;
import com.example.ddd.purchase.domain.model.PurchaseOrder;
import com.example.ddd.purchase.domain.model.PurchaseOrderLine;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseOrderCreatedEvent implements DomainEvent {
    private final String id;
    private final String companyId;
    private final BigDecimal limitAmount;
    private final List<PurchaseOrderLine> purchaseOrderLines;
    private final PurchaseOrder.PurchaseOrderState state;
    private final BigDecimal totalAmount;

    public PurchaseOrderCreatedEvent(String id, String companyId, BigDecimal limitAmount, List<PurchaseOrderLine> purchaseOrderLines, PurchaseOrder.PurchaseOrderState state, BigDecimal totalAmount) {
        this.id = id;
        this.companyId = companyId;
        this.limitAmount = limitAmount;
        this.purchaseOrderLines = purchaseOrderLines;
        this.state = state;
        this.totalAmount = totalAmount;
    }

    public static PurchaseOrderCreatedEvent create(String id, String companyId, BigDecimal limitAmount, List<PurchaseOrderLine> purchaseOrderLines, PurchaseOrder.PurchaseOrderState state, BigDecimal totalAmount){
        return new PurchaseOrderCreatedEvent(id, companyId, limitAmount, purchaseOrderLines, state, totalAmount);
    }

    public String getId() {
        return id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public List<PurchaseOrderLine> getPurchaseOrderLines() {
        return purchaseOrderLines;
    }

    public PurchaseOrder.PurchaseOrderState getState() {
        return state;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
