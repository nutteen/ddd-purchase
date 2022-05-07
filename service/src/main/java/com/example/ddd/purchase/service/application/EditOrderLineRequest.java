package com.example.ddd.purchase.service.application;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Introspected
public class EditOrderLineRequest {
    @NotNull
    private Integer lineOrder;
    @NotNull
    private Integer unit;
    @NotNull
    @Positive
    private BigDecimal unitPrice;
    @NotEmpty
    private String partId;

    public Integer getLineOrder() {
        return lineOrder;
    }

    public void setLineOrder(Integer lineOrder) {
        this.lineOrder = lineOrder;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }
}
