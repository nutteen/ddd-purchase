package com.example.ddd.purchase.domain.model;

import com.example.ddd.purchase.domain.api.*;
import com.example.ddd.purchase.domain.common.Aggregate;
import com.example.ddd.purchase.domain.model.state.PurchaseOrderEntity;
import com.example.ddd.purchase.domain.model.state.PurchaseOrderLineEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseOrder implements Aggregate<String, PurchaseOrderEntity> {
    private final String id;
    private final String companyId;
    private final List<PurchaseOrderLine> orderLines;
    private final BigDecimal limitAmount;
    private BigDecimal totalAmount;
    private Long version;

    private PurchaseOrder(String id, String companyId, BigDecimal limitAmount){
        this.id = id;
        this.companyId = companyId;
        this.orderLines = new ArrayList<>();
        this.totalAmount = BigDecimal.ZERO;
        this.limitAmount = limitAmount;
    }

    public static PurchaseOrder createWithCommand(CreatePurchaseOrderCommand command){
        var result = new PurchaseOrder(command.getId(), command.getCompanyId(), command.getLimitAmount());
        result.addOrderLines(command.getPurchaseOrderLines());
        return result;
    }

    public static PurchaseOrder createWithState(PurchaseOrderEntity state){
        var result = new PurchaseOrder(state.getId(), state.getCompanyId(), state.getLimitAmount());
        result.totalAmount = state.getTotalAmount();
        result.version = state.getVersion();
        for(var orderLineEntity : state.getOrderLines()){
            var orderLine = PurchaseOrderLine.create(
                    orderLineEntity.getId(),
                    orderLineEntity.getLineOrder(),
                    orderLineEntity.getPartId(),
                    orderLineEntity.getUnit(),
                    orderLineEntity.getUnitPrice());
            result.orderLines.add(orderLine);
        }
        result.orderLines.sort(Comparator.comparing(PurchaseOrderLine::getId));
        return result;
    }

    public void handle(AddOrderLinesCommand command){
        checkEditable();
        addOrderLines(command.getOrderLines());
        // Publish OrderLinesAdded event
    }

    public void handle(DeletePurchaseOrderCommand command){
        checkEditable();
    }

    public void handle(DeletePurchaseOrderLineCommand command){
        checkEditable();
        orderLines.remove(command.getLineId()-1);
    }

    public void handle(EditPurchaseOrderLineCommand command){
        checkEditable();
        orderLines.get(command.getLineId())
                .handle(command);
    }

    public void handle(SubmitPurchaseOrderCommand command){

    }

    private void checkEditable(){
        // TODO: add a proper state check

    }

    private void addOrderLines(List<PurchaseOrderLine> orderLines){
        var amountToAdd = orderLines
                .stream()
                .map(PurchaseOrderLine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var toBeAmount = totalAmount.add(amountToAdd);

        if(limitAmount.compareTo(toBeAmount) >= 0){
            this.orderLines.addAll(orderLines.stream()
                    .sorted(Comparator.comparing(PurchaseOrderLine::getId))
                    .collect(Collectors.toList()));
            totalAmount = toBeAmount;
        } else {
            throw new IllegalStateException("Amount to add exceeds po limit");
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public PurchaseOrderEntity getState() {
        var result = new PurchaseOrderEntity();
        result.setId(id);
        result.setCompanyId(companyId);
        result.setLimitAmount(limitAmount);
        result.setTotalAmount(totalAmount);
        result.setVersion(version);
        var orderLineEntities = new ArrayList<PurchaseOrderLineEntity>();
        for(var orderLine : orderLines){
            var orderLineEntity = new PurchaseOrderLineEntity();
            orderLineEntity.setPurchaseOrder(result);
            orderLineEntity.setId(orderLine.getId());
            orderLineEntity.setLineOrder(orderLine.getLineOrder());
            orderLineEntity.setPartId(orderLine.getPartId());
            orderLineEntity.setUnit(orderLine.getUnit());
            orderLineEntity.setUnitPrice(orderLine.getUnitPrice());
            orderLineEntities.add(orderLineEntity);
        }
        result.setOrderLines(orderLineEntities);
        return result;
    }

    public List<PurchaseOrderLine> getOrderLines() {
        return orderLines;
    }
}
