package com.example.ddd.purchase.domain.model.state;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "purchase_order_line")
@IdClass(PurchaseOrderLineEntity.PurchaseOrderLinePK.class)
public class PurchaseOrderLineEntity {
    @Id
    private String poId;
    @Id
    private Integer id;
    private int unit;
    private BigDecimal unitPrice;
    private String partId;

    public PurchaseOrderLineEntity(){
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public static class PurchaseOrderLinePK implements Serializable {
        private String poId;
        private Integer id;

        public PurchaseOrderLinePK(){
        }

        public PurchaseOrderLinePK(String poId, Integer id) {
            this.poId = poId;
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PurchaseOrderLinePK)) return false;
            PurchaseOrderLinePK that = (PurchaseOrderLinePK) o;
            return Objects.equals(poId, that.poId) && Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(poId, id);
        }
    }
}
