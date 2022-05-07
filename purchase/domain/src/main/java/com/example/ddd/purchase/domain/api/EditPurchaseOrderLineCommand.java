package com.example.ddd.purchase.domain.api;

import java.math.BigDecimal;

public class EditPurchaseOrderLineCommand {
    private final String poId;
    private final String lineId;
    private final Integer lineOrder;
    private final Integer unit;
    private final BigDecimal unitPrice;
    private final String partId;

    private EditPurchaseOrderLineCommand(String poId, String lineId, Integer lineOrder, Integer unit, BigDecimal unitPrice, String partId) {
        this.poId = poId;
        this.lineId = lineId;
        this.lineOrder = lineOrder;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.partId = partId;
    }

    public static EditPurchaseOrderLineCommand create(String poId, String lineId, Integer lineOrder, Integer unit, BigDecimal unitPrice, String partId) {
        return new EditPurchaseOrderLineCommand(poId, lineId, lineOrder, unit, unitPrice, partId);
    }

    public String getPoId() {
        return poId;
    }

    public String getLineId() {
        return lineId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getPartId() {
        return partId;
    }

    public Integer getLineOrder() {
        return lineOrder;
    }

    public Integer getUnit() {
        return unit;
    }
}
