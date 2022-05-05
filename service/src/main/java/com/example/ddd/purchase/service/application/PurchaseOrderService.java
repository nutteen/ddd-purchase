package com.example.ddd.purchase.service.application;

import com.example.ddd.purchase.domain.api.*;
import com.example.ddd.purchase.domain.model.PurchaseOrder;
import com.example.ddd.purchase.domain.model.PurchaseOrderLine;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderDto;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderLineDto;
import com.example.ddd.purchase.domain.repository.PurchaseOrderRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Singleton
public class PurchaseOrderService {

    @Inject
    PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    public CreatePurchaseOrderResponse createPurchaseOrder(CreatePurchaseOrderRequest request){
        var command = new CreatePurchaseOrderCommand();
        command.setId(UUID.randomUUID().toString());
        command.setLimitAmount(request.getLimitAmount());

        var orderLines = new ArrayList<PurchaseOrderLine>();
        var i = 1;
        for(var orderLineDto : request.getPurchaseOrderLines()){
            var orderLine = PurchaseOrderLine.create(i++,
                    orderLineDto.getPartId(),
                    orderLineDto.getUnit(),
                    orderLineDto.getUnitPrice());
            orderLines.add(orderLine);
        }
        command.setPurchaseOrderLines(orderLines);

        var purchaseOrder = PurchaseOrder.createWithCommand(command);
        purchaseOrderRepository.save(purchaseOrder);

        var response = new CreatePurchaseOrderResponse();
        response.setId(purchaseOrder.getId());

        return response;
    }

    @Transactional
    public void addOrderLines(String id, AddOrderLinesRequest request){
        var command = new AddOrderLinesCommand();
        command.setId(id);
        var orderLines = new ArrayList<PurchaseOrderLine>();
        var i = 1;
        for(var orderLineDto : request.getOrderLines()){
            var orderLine = PurchaseOrderLine.create(
                    i++,
                    orderLineDto.getPartId(),
                    orderLineDto.getUnit(),
                    orderLineDto.getUnitPrice());
            orderLines.add(orderLine);
        }
        command.setOrderLines(orderLines);

        var purchaseOrder = fetchOrderById(command.getId());
        purchaseOrder.handle(command);
        purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public void deletePurchaseOrder(String id){
        var command = new DeletePurchaseOrderCommand();
        command.setId(id);
        var purchaseOrder = fetchOrderById(id);
        purchaseOrder.handle(command);
        purchaseOrderRepository.delete(purchaseOrder);
    }

    @Transactional
    public void deletePurchaseOrderLine(String id, int orderLineId){
        var command = new DeletePurchaseOrderLineCommand();
        command.setPoId(id);
        command.setLineId(orderLineId);

        var purchaseOrder = fetchOrderById(command.getPoId());
        purchaseOrder.handle(command);
        purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public void editPurchaseOrderLine(String poId, int orderLineId, EditOrderLineRequest request){
        var command = new EditPurchaseOrderLineCommand();
        command.setPoId(poId);
        command.setLineId(orderLineId);
        command.setUnit(request.getUnit());
        command.setPartId(request.getPartId());
        command.setUnitPrice(request.getUnitPrice());

        var purchaseOrder = fetchOrderById(command.getPoId());
        purchaseOrder.handle(command);
        purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public void submitPurchaseOrder(String id){
        var command = new SubmitPurchaseOrderCommand();
        command.setId(id);

        var purchaseOrder = fetchOrderById(command.getId());
        purchaseOrder.handle(command);
        purchaseOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrderDto> getPurchaseOrders(){
        var line = new PurchaseOrderLineDto();
        line.setId(1);
        line.setPartId("1");
        line.setUnit(2);
        line.setUnitPrice(BigDecimal.valueOf(250));
        var po = new PurchaseOrderDto();
        po.setId(UUID.randomUUID().toString());
        po.setOrderLines(Collections.singletonList(line));
        return Collections.singletonList(po);
    }

    private PurchaseOrder fetchOrderById(String id){
        var optionalPurchaseOrder = purchaseOrderRepository.findById(id);
        if(optionalPurchaseOrder.isPresent()){
            return optionalPurchaseOrder.get();
        } else {
            throw new RuntimeException("Order id: " + id + " not found");
        }
    }
}
