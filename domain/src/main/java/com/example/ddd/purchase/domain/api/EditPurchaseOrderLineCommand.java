package com.example.ddd.purchase.domain.api;

import java.math.BigDecimal;

public class EditPurchaseOrderLineCommand {
    private final String poId;
    private final int lineId;
    private final int unit;
    private final BigDecimal unitPrice;
    private final String partId;

    private EditPurchaseOrderLineCommand(String poId, int lineId, int unit, BigDecimal unitPrice, String partId) {
        this.poId = poId;
        this.lineId = lineId;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.partId = partId;
    }

    public static EditPurchaseOrderLineCommand create(String poId, int lineId, int unit, BigDecimal unitPrice, String partId) {
        return new EditPurchaseOrderLineCommand(poId, lineId, unit, unitPrice, partId);
    }

    public String getPoId() {
        return poId;
    }

    public int getLineId() {
        return lineId;
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
