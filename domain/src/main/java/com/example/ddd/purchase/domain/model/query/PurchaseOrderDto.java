package com.example.ddd.purchase.domain.model.query;

import io.micronaut.core.annotation.Introspected;

import java.math.BigDecimal;
import java.util.List;

@Introspected // require for projection
public class PurchaseOrderDto {
    private String id;
    private List<PurchaseOrderLineDto> orderLines;
    private BigDecimal totalAmount;
    private BigDecimal limitAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PurchaseOrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<PurchaseOrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }
}
