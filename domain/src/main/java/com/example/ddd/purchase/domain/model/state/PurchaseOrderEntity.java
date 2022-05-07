package com.example.ddd.purchase.domain.model.state;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_order")
public class PurchaseOrderEntity {
    @Id
    private String id;
    private String companyId;
    @OneToMany(
            mappedBy = "purchaseOrder",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<PurchaseOrderLineEntity> orderLines = new ArrayList<>();
    private BigDecimal limitAmount;
    private BigDecimal totalAmount;
    @Version
    private Long version;

    public PurchaseOrderEntity(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<PurchaseOrderLineEntity> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<PurchaseOrderLineEntity> orderLines) {
        this.orderLines = orderLines;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
