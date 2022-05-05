package com.example.ddd.purchase.service.controller;

import com.example.ddd.purchase.domain.model.query.PurchaseOrderDto;
import com.example.ddd.purchase.service.application.*;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Controller("/purchase-orders")
@Validated
public class PurchaseOrderController {

    @Inject
    PurchaseOrderService purchaseOrderService;

    @Post
    public CreatePurchaseOrderResponse createPurchaseOrder(@Body @Valid CreatePurchaseOrderRequest request){
        return purchaseOrderService.createPurchaseOrder(request);
    }

    @Post("/{id}/order-lines")
    public void addOrderLines(String id, @Body @Valid AddOrderLinesRequest request){
        purchaseOrderService.addOrderLines(id, request);
    }

    @Delete("/{id}")
    public void deleteOrder(String id){
        purchaseOrderService.deletePurchaseOrder(id);
    }

    @Delete("/{id}/order-lines/{orderLineId}")
    public void deleteOrderLine(String id, @Positive int orderLineId){
        purchaseOrderService.deletePurchaseOrderLine(id, orderLineId);
    }

    @Put("/{id}/order-lines/{orderLineId}")
    public void editOrderLine(String id, @Positive int orderLineId, @Body @Valid EditOrderLineRequest request){
        purchaseOrderService.editPurchaseOrderLine(id, orderLineId, request);
    }

    @Post("/{id}/submit")
    public void submitOrder(String id){
        purchaseOrderService.submitPurchaseOrder(id);
    }

    @Get
    public List<PurchaseOrderDto> getPurchaseOrders(){
        return purchaseOrderService.getPurchaseOrders();
    }
}
