/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author oracle
 */
@Entity
@Cacheable(false)
@Table(name = "RECEPT_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReceptDtl.findAll", query = "SELECT r FROM ReceptDtl r"),
    @NamedQuery(name = "ReceptDtl.findByDocid", query = "SELECT r FROM ReceptDtl r WHERE r.receptDtlPK.docid = :docid"),
    @NamedQuery(name = "ReceptDtl.findByBarcode", query = "SELECT r FROM ReceptDtl r WHERE r.receptDtlPK.barcode = :barcode"),
    @NamedQuery(name = "ReceptDtl.findByQty", query = "SELECT r FROM ReceptDtl r WHERE r.qty = :qty"),
    @NamedQuery(name = "ReceptDtl.findByPurchasePrice", query = "SELECT r FROM ReceptDtl r WHERE r.purchasePrice = :purchasePrice"),
    @NamedQuery(name = "ReceptDtl.findByItemDiscount", query = "SELECT r FROM ReceptDtl r WHERE r.itemDiscount = :itemDiscount")})
public class ReceptDtl implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReceptDtlPK receptDtlPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "QTY")
    private BigDecimal qty;
    @Column(name = "PURCHASE_PRICE")
    private BigDecimal purchasePrice;
    @Column(name = "ITEM_DISCOUNT")
    private BigDecimal itemDiscount;
    @JoinColumn(name = "DOCID", referencedColumnName = "DOCID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Recept recept;
    @JoinColumn(name = "BARCODE", referencedColumnName = "BARCODE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;

    public ReceptDtl() {
    }

    public ReceptDtl(ReceptDtlPK receptDtlPK) {
        this.receptDtlPK = receptDtlPK;
    }

    public ReceptDtl(String docid, String barcode) {
        this.receptDtlPK = new ReceptDtlPK(docid, barcode);
    }

    public ReceptDtlPK getReceptDtlPK() {
        return receptDtlPK;
    }

    public void setReceptDtlPK(ReceptDtlPK receptDtlPK) {
        this.receptDtlPK = receptDtlPK;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(BigDecimal itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public Recept getRecept() {
        return recept;
    }

    public void setRecept(Recept recept) {
        this.recept = recept;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receptDtlPK != null ? receptDtlPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceptDtl)) {
            return false;
        }
        ReceptDtl other = (ReceptDtl) object;
        if ((this.receptDtlPK == null && other.receptDtlPK != null) || (this.receptDtlPK != null && !this.receptDtlPK.equals(other.receptDtlPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ReceptDtl[ receptDtlPK=" + receptDtlPK + " ]";
    }
    
}
