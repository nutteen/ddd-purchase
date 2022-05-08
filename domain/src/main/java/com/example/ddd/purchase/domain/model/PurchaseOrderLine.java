package com.example.ddd.purchase.domain.model;

import com.example.ddd.purchase.domain.api.command.EditPurchaseOrderLineCommand;

import java.math.BigDecimal;
import java.util.Objects;

public class PurchaseOrderLine {
    private final String id;
    private int lineOrder;
    private int unit;
    private BigDecimal unitPrice;
    private String partId;

    private PurchaseOrderLine(String id, int lineOrder){
        this.id = id;
        this.lineOrder = lineOrder;
        unit = 0;
        unitPrice = BigDecimal.ZERO;
    }

    public static PurchaseOrderLine create(String id, int lineOrder, String partId, int unit, BigDecimal unitPrice){
        Objects.requireNonNull(id);
        Objects.requireNonNull(partId);
        Objects.requireNonNull(unitPrice);
        if(unit <= 0 || unitPrice.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalStateException("unit price and unit can't be less than 0");

        var result = new PurchaseOrderLine(id, lineOrder);
        result.partId = partId;
        result.unit = unit;
        result.unitPrice = unitPrice;
        return result;
    }

    public void handle(EditPurchaseOrderLineCommand command){
        Objects.requireNonNull(command.getUnit());
        Objects.requireNonNull(command.getUnitPrice());
        Objects.requireNonNull(command.getPartId());
        Objects.requireNonNull(command.getLineOrder());
        if(command.getUnit() <= 0){
            throw new IllegalStateException("Unit must be > 0");
        }
        if(command.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalStateException("Unit price must be > 0");
        }
        if(command.getLineOrder() <= 0){
            throw new IllegalStateException("line order must be > 0");
        }

        this.unit = command.getUnit();
        this.unitPrice = command.getUnitPrice();
        this.partId = command.getPartId();
        this.lineOrder = command.getLineOrder();
    }

    public String getId() {
        return id;
    }

    public int getLineOrder() {
        return lineOrder;
    }

    public BigDecimal getAmount(){
        return unitPrice.multiply(BigDecimal.valueOf(unit));
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
