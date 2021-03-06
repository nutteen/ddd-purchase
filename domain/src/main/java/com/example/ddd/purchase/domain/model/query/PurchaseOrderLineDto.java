package com.example.ddd.purchase.domain.model.query;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Introspected
public class PurchaseOrderLineDto {
    private String id;
    @NotNull
    private Integer lineOrder;
    @NotNull
    private Integer unit;
    @NotNull
    @Positive
    private BigDecimal unitPrice;
    @NotBlank
    private String partId;

    public PurchaseOrderLineDto(){
    }

    public PurchaseOrderLineDto(String id, Integer lineOrder, Integer unit, BigDecimal unitPrice, String partId) {
        this.id = id;
        this.lineOrder = lineOrder;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.partId = partId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLineOrder() {
        return lineOrder;
    }

    public void setLineOrder(Integer lineOrder) {
        this.lineOrder = lineOrder;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }
}
