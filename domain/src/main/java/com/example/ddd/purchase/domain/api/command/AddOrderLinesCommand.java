package com.example.ddd.purchase.domain.api.command;

import com.example.ddd.purchase.domain.model.PurchaseOrderLine;

import java.util.List;

public class AddOrderLinesCommand {
    private final String id;
    private final List<PurchaseOrderLine> orderLines;

    private AddOrderLinesCommand(String id, List<PurchaseOrderLine> orderLines){
        this.id = id;
        this.orderLines = orderLines;
    }

    public static AddOrderLinesCommand create(String id, List<PurchaseOrderLine> orderLines){
        return new AddOrderLinesCommand(id, orderLines);
    }

    public String getId(){
        return id;
    }

    public List<PurchaseOrderLine> getOrderLines(){
        return this.orderLines;
    }
}
