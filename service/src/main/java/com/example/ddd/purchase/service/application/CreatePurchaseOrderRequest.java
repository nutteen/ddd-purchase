package com.example.ddd.purchase.service.application;

import com.example.ddd.purchase.domain.model.query.PurchaseOrderLineDto;
import io.micronaut.core.annotation.Introspected;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Introspected
public class CreatePurchaseOrderRequest {
    @NotNull
    @Positive
    private BigDecimal limitAmount;

    @NotNull
    @Valid
    private List<PurchaseOrderLineDto> purchaseOrderLines;

    @NotNull
    private String companyId;

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public List<PurchaseOrderLineDto> getPurchaseOrderLines() {
        return purchaseOrderLines;
    }

    public void setPurchaseOrderLines(List<PurchaseOrderLineDto> purchaseOrderLines) {
        this.purchaseOrderLines = purchaseOrderLines;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
