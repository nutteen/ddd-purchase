package com.example.ddd.purchase.domain.api;

import com.example.ddd.purchase.domain.model.PurchaseOrderLine;

import java.math.BigDecimal;
import java.util.List;

public class CreatePurchaseOrderCommand {
    private String id;
    private BigDecimal limitAmount;
    private List<PurchaseOrderLine> purchaseOrderLines;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setLimitAmount(BigDecimal limitAmount){
        this.limitAmount = limitAmount;
    }

    public BigDecimal getLimitAmount(){
        return limitAmount;
    }

    public void setPurchaseOrderLines(List<PurchaseOrderLine> purchaseOrderLines){
        this.purchaseOrderLines = purchaseOrderLines;
    }

    public List<PurchaseOrderLine> getPurchaseOrderLines(){
        return purchaseOrderLines;
    }
}
