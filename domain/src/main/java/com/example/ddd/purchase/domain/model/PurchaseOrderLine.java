package com.example.ddd.purchase.domain.model;

import com.example.ddd.purchase.domain.api.EditPurchaseOrderLineCommand;

import java.math.BigDecimal;
import java.util.Objects;

public class PurchaseOrderLine {
    private int id;
    private int unit;
    private BigDecimal unitPrice;
    private String partId;

    private PurchaseOrderLine(){
    }

    private PurchaseOrderLine(int id){
        this.id = id;
        unit = 0;
        unitPrice = BigDecimal.ZERO;
    }

    public static PurchaseOrderLine create(int id, String partId, int unit, BigDecimal unitPrice){
        Objects.requireNonNull(partId);
        Objects.requireNonNull(unitPrice);
        if(unit <= 0 || unitPrice.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalStateException("unit price and unit can't be less than 0");

        var result = new PurchaseOrderLine(id);
        result.partId = partId;
        result.unit = unit;
        result.unitPrice = unitPrice;
        return result;
    }

    public void handle(EditPurchaseOrderLineCommand command){

    }

    public BigDecimal getAmount(){
        return unitPrice.multiply(BigDecimal.valueOf(unit));
    }

    public int getId(){
        return id;
    }

    public int getUnit() {
        return unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getPartId() {
        return partId;
    }
}
