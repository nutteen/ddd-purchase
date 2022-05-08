package com.example.ddd.purchase.domain.api.command;

import com.example.ddd.purchase.domain.model.PurchaseOrderLine;

import java.math.BigDecimal;
import java.util.List;

public class CreatePurchaseOrderCommand {
    private final String id;
    private final String companyId;
    private final BigDecimal limitAmount;
    private final List<PurchaseOrderLine> purchaseOrderLines;

    private CreatePurchaseOrderCommand(String id, String companyId, BigDecimal limitAmount, List<PurchaseOrderLine> purchaseOrderLines) {
        this.id = id;
        this.companyId = companyId;
        this.limitAmount = limitAmount;
        this.purchaseOrderLines = purchaseOrderLines;
    }

    public static CreatePurchaseOrderCommand create(String id, String companyId, BigDecimal limitAmount, List<PurchaseOrderLine> purchaseOrderLines){
        return new CreatePurchaseOrderCommand(id, companyId, limitAmount, purchaseOrderLines);
    }

    public String getId(){
        return id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public BigDecimal getLimitAmount(){
        return limitAmount;
    }

    public List<PurchaseOrderLine> getPurchaseOrderLines(){
        return purchaseOrderLines;
    }
}
