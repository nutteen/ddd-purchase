package com.example.ddd.purchase.domain.model;

import com.example.ddd.purchase.domain.api.command.*;
import com.example.ddd.purchase.domain.common.Aggregate;
import com.example.ddd.purchase.domain.model.state.PurchaseOrderEntity;
import com.example.ddd.purchase.domain.model.state.PurchaseOrderLineEntity;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PurchaseOrder implements Aggregate<String, PurchaseOrderEntity> {
    private final String id;
    private final String companyId;
    private final Map<String, PurchaseOrderLine> orderLines = new HashMap<>();
    private final BigDecimal limitAmount;
    private final Long version;
    private PurchaseOrderState state;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    private PurchaseOrder(String id, String companyId, BigDecimal limitAmount, PurchaseOrderState state, Long version){
        this.id = id;
        this.companyId = companyId;
        this.limitAmount = limitAmount;
        this.version = version;
        this.state = state;
    }

    public static PurchaseOrder createWithCommand(CreatePurchaseOrderCommand command){
        var result = new PurchaseOrder(command.getId(), command.getCompanyId(), command.getLimitAmount(), PurchaseOrderState.CREATED, null);
        result.addOrderLines(command.getPurchaseOrderLines());
        return result;
    }

    public static PurchaseOrder createWithState(PurchaseOrderEntity state){
        var result = new PurchaseOrder(state.getId(), state.getCompanyId(), state.getLimitAmount(), state.getState(), state.getVersion());
        result.totalAmount = state.getTotalAmount();
        for(var orderLineEntity : state.getOrderLines()){
            var orderLine = PurchaseOrderLine.create(
                    orderLineEntity.getId(),
                    orderLineEntity.getLineOrder(),
                    orderLineEntity.getPartId(),
                    orderLineEntity.getUnit(),
                    orderLineEntity.getUnitPrice());
            result.orderLines.put(orderLine.getId(), orderLine);
        }
        return result;
    }

    public void handle(AddOrderLinesCommand command){
        checkEditable();
        addOrderLines(command.getOrderLines());
    }

    public void handle(DeletePurchaseOrderLineCommand command){
        checkEditable();
        if(orderLines.containsKey(command.getLineId())){
            var removedLine = orderLines.remove(command.getLineId());
            totalAmount = totalAmount.subtract(removedLine.getAmount());
        } else {
            throw new IllegalStateException("line id: " + command.getLineId() + " not found in po: " + command.getPoId());
        }
    }

    public void handle(DeletePurchaseOrderCommand command){
        checkEditable();
    }

    public void handle(EditPurchaseOrderLineCommand command){
        checkEditable();
        if(orderLines.containsKey(command.getLineId())){
            var orderLine = orderLines.get(command.getLineId());
            var oldAmount = orderLine.getAmount();
            orderLine.handle(command);

            var hasAmountChanged = !oldAmount.equals(orderLine.getAmount());
            if(hasAmountChanged){
                refreshTotalAmount();
            }
        } else {
            throw new IllegalStateException("line id: " + command.getLineId() + " not found in po: " + command.getPoId());
        }
    }

    public void handle(SubmitPurchaseOrderCommand command){
        if(state == PurchaseOrderState.SUBMITTED){
            throw new IllegalStateException("PO was submitted already, and can't be submitted again");
        }
        if(orderLines.isEmpty()){
            throw new IllegalStateException("Can't submit PO without any order lines");
        }
        state = PurchaseOrderState.SUBMITTED;
    }

    private void checkEditable(){
        if(state == PurchaseOrderState.SUBMITTED){
            throw new IllegalStateException("Submitted PO can't be modified or deleted");
        }
    }

    private void refreshTotalAmount(){
        this.totalAmount = orderLines
                .values()
                .stream()
                .map(PurchaseOrderLine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void addOrderLines(List<PurchaseOrderLine> orderLines){
        var amountToAdd = orderLines
                .stream()
                .map(PurchaseOrderLine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var toBeAmount = totalAmount.add(amountToAdd);

        if(limitAmount.compareTo(toBeAmount) >= 0){
            this.orderLines.putAll(
                    orderLines.stream()
                            .collect(Collectors.toMap(PurchaseOrderLine::getId, e-> e)));
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
        result.setState(state);
        var orderLineEntities = new ArrayList<PurchaseOrderLineEntity>();
        for(var orderLineEntry : orderLines.entrySet()){
            var orderLine = orderLineEntry.getValue();
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

    public enum PurchaseOrderState {
        CREATED, SUBMITTED
    }
}
