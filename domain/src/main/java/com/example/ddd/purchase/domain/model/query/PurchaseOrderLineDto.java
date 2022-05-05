package com.example.ddd.purchase.domain.model.query;

import io.micronaut.core.annotation.Introspected;

import java.math.BigDecimal;

@Introspected
public class PurchaseOrderLineDto {
    private Integer id;
    private String partId;
    private BigDecimal unitPrice;
    private int unit;

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public BigDecimal getAmount() {
        return unitPrice.multiply(BigDecimal.valueOf(unit));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}
