package com.example.ddd.purchase.domain.model.query;

import com.example.ddd.purchase.domain.model.PurchaseOrder;
import io.micronaut.core.annotation.Introspected;

import java.math.BigDecimal;

@Introspected // require for projection
public class PurchaseOrderDto {
    private String id;
    private String companyId;
    private BigDecimal totalAmount;
    private BigDecimal limitAmount;
    private Integer version;
    private PurchaseOrder.PurchaseOrderState state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public PurchaseOrder.PurchaseOrderState getState() {
        return state;
    }

    public void setState(PurchaseOrder.PurchaseOrderState state) {
        this.state = state;
    }
}
