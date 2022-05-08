package com.example.ddd.purchase.domain.api.command;

public class DeletePurchaseOrderCommand {
    private final String id;

    private DeletePurchaseOrderCommand(String id) {
        this.id = id;
    }

    public static DeletePurchaseOrderCommand create(String id){
        return new DeletePurchaseOrderCommand(id);
    }

    public String getId() {
        return id;
    }
}
