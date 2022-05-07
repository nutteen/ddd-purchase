package com.example.ddd.purchase.domain.model.state;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_line")
public class PurchaseOrderLineEntity {
    @Id
    private String id;
    private String poId;
    private Integer lineOrder;
    private Integer unit;
    private BigDecimal unitPrice;
    private String partId;

    public PurchaseOrderLineEntity(){
    }

    public String getPoId() {
        return poId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLineOrder() {
        return lineOrder;
    }

    public void setLineOrder(Integer lineOrder) {
        this.lineOrder = lineOrder;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }
}
