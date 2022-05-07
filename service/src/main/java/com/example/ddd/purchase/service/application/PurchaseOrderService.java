package com.example.ddd.purchase.service.application;

import com.example.ddd.purchase.domain.api.*;
import com.example.ddd.purchase.domain.model.PurchaseOrder;
import com.example.ddd.purchase.domain.model.PurchaseOrderLine;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderDto;
import com.example.ddd.purchase.domain.repository.PurchaseOrderRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.data.model.Sort;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.UUID;

@Singleton
public class PurchaseOrderService {

    @Inject
    PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    public CreatePurchaseOrderResponse createPurchaseOrder(CreatePurchaseOrderRequest request){
        var command = new CreatePurchaseOrderCommand();
        command.setId(UUID.randomUUID().toString());
        command.setCompanyId(request.getCompanyId());
        command.setLimitAmount(request.getLimitAmount());

        var orderLines = new ArrayList<PurchaseOrderLine>();
        for(var orderLineDto : request.getPurchaseOrderLines()){
            var orderLine = PurchaseOrderLine.create(orderLineDto.getId(),
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
        for(var orderLineDto : request.getOrderLines()){
            var orderLine = PurchaseOrderLine.create(
                    orderLineDto.getId(),
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

    public Slice<PurchaseOrderDto> findPurchaseOrderByCompanyId(String companyId, int page, int size, Sort sort){
        var pageable = Pageable.from(page, size, sort);
        return purchaseOrderRepository.findPurchaseOrderDtoByCompanyId(companyId, pageable);
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
