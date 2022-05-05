package com.example.ddd.purchase.domain.api;

public class DeletePurchaseOrderLineCommand {
    private String poId;
    private int lineId;

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }
}
