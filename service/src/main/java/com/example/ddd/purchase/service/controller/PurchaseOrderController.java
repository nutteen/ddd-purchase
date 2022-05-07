package com.example.ddd.purchase.service.controller;

import com.example.ddd.purchase.domain.model.query.PurchaseOrderDto;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderLineDto;
import com.example.ddd.purchase.service.application.*;
import io.micronaut.data.model.Slice;
import io.micronaut.data.model.Sort;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;

import javax.validation.Valid;
import java.util.Optional;

@Controller("/purchase-orders")
@Validated
public class PurchaseOrderController {

    @Inject
    PurchaseOrderService purchaseOrderService;

    @Post
    public CreatePurchaseOrderResponse createPurchaseOrder(@Body @Valid CreatePurchaseOrderRequest request){
        return purchaseOrderService.createPurchaseOrder(request);
    }

    @Post("/{id}/add-order-lines")
    public void addOrderLines(String id, @Body @Valid AddOrderLinesRequest request){
        purchaseOrderService.addOrderLines(id, request);
    }

    @Get("/{id}")
    public PurchaseOrderDto findByPurchaseOrderById(String id){
        var optionalPurchaseOrder = purchaseOrderService.findPurchaseOrderById(id);
        if(optionalPurchaseOrder.isPresent()){
            return optionalPurchaseOrder.get();
        } else {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "Purchase order id: " + id + " not found");
        }
    }

    @Delete("/{id}")
    public void deleteOrder(String id){
        purchaseOrderService.deletePurchaseOrder(id);
    }

    @Delete("/{id}/order-lines/{orderLineId}")
    public void deleteOrderLine(String id, String orderLineId){
        purchaseOrderService.deletePurchaseOrderLine(id, orderLineId);
    }

    @Put("/{id}/order-lines/{orderLineId}")
    public void editOrderLine(String id, String orderLineId, @Body @Valid EditOrderLineRequest request){
        purchaseOrderService.editPurchaseOrderLine(id, orderLineId, request);
    }

    @Post("/{id}/submit")
    public void submitOrder(String id){
        purchaseOrderService.submitPurchaseOrder(id);
    }

    @Get
    public Slice<PurchaseOrderDto> findPOSliceByCompanyId(@QueryValue String companyId,
                                                          @QueryValue Optional<Integer> page,
                                                          @QueryValue Optional<Integer> size,
                                                          @QueryValue Optional<Sort> sort){
        return purchaseOrderService.findPurchaseOrderByCompanyId(companyId, page.orElse(0), size.orElse(10), sort.orElse(Sort.unsorted()));
    }

    @Get("/{id}/order-lines")
    public Slice<PurchaseOrderLineDto> findOrderLineSliceByPO(String id,
                                                              @QueryValue Optional<Integer> page,
                                                              @QueryValue Optional<Integer> size,
                                                              @QueryValue Optional<Sort> sort){
        return purchaseOrderService.findOrderLineSliceByPO(id, page.orElse(0), size.orElse(10), sort.orElse(Sort.unsorted()));
    }
}
