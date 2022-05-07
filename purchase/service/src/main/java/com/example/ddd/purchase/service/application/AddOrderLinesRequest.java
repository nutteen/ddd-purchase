package com.example.ddd.purchase.service.application;

import com.example.ddd.purchase.domain.model.query.PurchaseOrderLineDto;
import io.micronaut.core.annotation.Introspected;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Introspected
public class AddOrderLinesRequest {
    @NotNull
    @Valid
    private List<PurchaseOrderLineDto> orderLines;

    public List<PurchaseOrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<PurchaseOrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }
}
