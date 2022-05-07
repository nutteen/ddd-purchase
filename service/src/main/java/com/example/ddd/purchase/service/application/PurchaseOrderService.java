package com.example.ddd.purchase.service.application;

import com.example.ddd.purchase.domain.api.*;
import com.example.ddd.purchase.domain.model.PurchaseOrder;
import com.example.ddd.purchase.domain.model.PurchaseOrderLine;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderDto;
import com.example.ddd.purchase.domain.model.query.PurchaseOrderLineDto;
import com.example.ddd.purchase.domain.repository.PurchaseOrderRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.data.model.Sort;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class PurchaseOrderService {

    @Inject
    PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    public CreatePurchaseOrderResponse createPurchaseOrder(CreatePurchaseOrderRequest request){
        var command = CreatePurchaseOrderCommand.create(
                UUID.randomUUID().toString(), request.getCompanyId(), request.getLimitAmount(),
                createOrderLinesFromDto(request.getPurchaseOrderLines()));

        var purchaseOrder = PurchaseOrder.createWithCommand(command);
        purchaseOrderRepository.save(purchaseOrder);

        var response = new CreatePurchaseOrderResponse();
        response.setId(purchaseOrder.getId());

        return response;
    }

    @Transactional
    public void addOrderLines(String id, AddOrderLinesRequest request){
        var command = AddOrderLinesCommand.create(id, createOrderLinesFromDto(request.getOrderLines()));
        var purchaseOrder = fetchOrderById(command.getId());
        purchaseOrder.handle(command);
        purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public void deletePurchaseOrder(String id){
        var command = DeletePurchaseOrderCommand.create(id);
        var purchaseOrder = fetchOrderById(id);
        purchaseOrder.handle(command);
        purchaseOrderRepository.delete(purchaseOrder);
    }

    @Transactional
    public void deletePurchaseOrderLine(String id, String orderLineId){
        var command = DeletePurchaseOrderLineCommand.create(id, orderLineId);
        var purchaseOrder = fetchOrderById(command.getPoId());
        purchaseOrder.handle(command);
        purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public void editPurchaseOrderLine(String poId, int orderLineId, EditOrderLineRequest request){
        var command = EditPurchaseOrderLineCommand.create(
                poId, orderLineId, request.getUnit(), request.getUnitPrice(), request.getPartId());
        var purchaseOrder = fetchOrderById(command.getPoId());
        purchaseOrder.handle(command);
        purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public void submitPurchaseOrder(String id){
        var command = SubmitPurchaseOrderCommand.create(id);
        var purchaseOrder = fetchOrderById(command.getId());
        purchaseOrder.handle(command);
        purchaseOrderRepository.save(purchaseOrder);
    }

    public Slice<PurchaseOrderDto> findPurchaseOrderByCompanyId(String companyId, int page, int size, Sort sort){
        return purchaseOrderRepository.findPurchaseOrderDtoByCompanyId(companyId, Pageable.from(page, size, sort));
    }

    public Optional<PurchaseOrderDto> findPurchaseOrderById(String id){
        return purchaseOrderRepository.findPurchaseOrderDtoById(id);
    }

    private PurchaseOrder fetchOrderById(String id){
        var optionalPurchaseOrder = purchaseOrderRepository.findById(id);
        if(optionalPurchaseOrder.isPresent()){
            return optionalPurchaseOrder.get();
        } else {
            throw new RuntimeException("Order id: " + id + " not found");
        }
    }

    private List<PurchaseOrderLine> createOrderLinesFromDto(List<PurchaseOrderLineDto> purchaseOrderLineDtos){
        var result = new ArrayList<PurchaseOrderLine>();
        for(var orderLineDto : purchaseOrderLineDtos){
            var orderLine = PurchaseOrderLine.create(
                    UUID.randomUUID().toString(),
                    orderLineDto.getLineOrder(),
                    orderLineDto.getPartId(),
                    orderLineDto.getUnit(),
                    orderLineDto.getUnitPrice());
            result.add(orderLine);
        }
        return result;
    }
}
