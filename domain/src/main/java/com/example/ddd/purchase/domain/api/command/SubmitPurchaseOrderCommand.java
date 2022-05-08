package com.example.ddd.purchase.domain.api.command;

public class SubmitPurchaseOrderCommand {
    private final String id;

    private SubmitPurchaseOrderCommand(String id) {
        this.id = id;
    }

    public static SubmitPurchaseOrderCommand create(String id){
        return new SubmitPurchaseOrderCommand(id);
    }

    public String getId() {
        return id;
    }
}
