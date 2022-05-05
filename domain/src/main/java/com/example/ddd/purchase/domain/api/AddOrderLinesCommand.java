package com.example.ddd.purchase.domain.api;

import com.example.ddd.purchase.domain.model.PurchaseOrderLine;

import java.util.List;

public class AddOrderLinesCommand {
    private String id;
    private List<PurchaseOrderLine> orderLines;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setOrderLines(List<PurchaseOrderLine> orderLines){
        this.orderLines = orderLines;
    }

    public List<PurchaseOrderLine> getOrderLines(){
        return this.orderLines;
    }
}
