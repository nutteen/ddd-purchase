package com.example.ddd.purchase.domain.api;

public class DeletePurchaseOrderLineCommand {
    private final String poId;
    private final int lineId;

    private DeletePurchaseOrderLineCommand(String poId, int lineId) {
        this.poId = poId;
        this.lineId = lineId;
    }

    public static DeletePurchaseOrderLineCommand create(String poId, int lineId){
        return new DeletePurchaseOrderLineCommand(poId, lineId);
    }

    public String getPoId() {
        return poId;
    }

    public int getLineId() {
        return lineId;
    }
}
