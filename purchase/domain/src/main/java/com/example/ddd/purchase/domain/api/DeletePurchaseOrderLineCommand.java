package com.example.ddd.purchase.domain.api;

public class DeletePurchaseOrderLineCommand {
    private final String poId;
    private final String lineId;

    private DeletePurchaseOrderLineCommand(String poId, String lineId) {
        this.poId = poId;
        this.lineId = lineId;
    }

    public static DeletePurchaseOrderLineCommand create(String poId, String lineId){
        return new DeletePurchaseOrderLineCommand(poId, lineId);
    }

    public String getPoId() {
        return poId;
    }

    public String getLineId() {
        return lineId;
    }
}
