/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
@Table(name = "PURCHASES_DTL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PurchasesDtl.findAll", query = "SELECT p FROM PurchasesDtl p"),
    @NamedQuery(name = "PurchasesDtl.findByDocid", query = "SELECT p FROM PurchasesDtl p WHERE p.purchasesDtlPK.docid = :docid"),
    @NamedQuery(name = "PurchasesDtl.findByBarcode", query = "SELECT p FROM PurchasesDtl p WHERE p.purchasesDtlPK.barcode = :barcode"),
    @NamedQuery(name = "PurchasesDtl.findByQty", query = "SELECT p FROM PurchasesDtl p WHERE p.qty = :qty")})
public class PurchasesDtl implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PurchasesDtlPK purchasesDtlPK;
    @Column(name = "QTY")
    private Integer qty;
    @JoinColumn(name = "DOCID", referencedColumnName = "DOCID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Purchases purchases;

    public PurchasesDtl() {
    }

    public PurchasesDtl(PurchasesDtlPK purchasesDtlPK) {
        this.purchasesDtlPK = purchasesDtlPK;
    }

    public PurchasesDtl(String docid, String barcode) {
        this.purchasesDtlPK = new PurchasesDtlPK(docid, barcode);
    }

    public PurchasesDtlPK getPurchasesDtlPK() {
        return purchasesDtlPK;
    }

    public void setPurchasesDtlPK(PurchasesDtlPK purchasesDtlPK) {
        this.purchasesDtlPK = purchasesDtlPK;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Purchases getPurchases() {
        return purchases;
    }

    public void setPurchases(Purchases purchases) {
        this.purchases = purchases;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purchasesDtlPK != null ? purchasesDtlPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchasesDtl)) {
            return false;
        }
        PurchasesDtl other = (PurchasesDtl) object;
        if ((this.purchasesDtlPK == null && other.purchasesDtlPK != null) || (this.purchasesDtlPK != null && !this.purchasesDtlPK.equals(other.purchasesDtlPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PurchasesDtl[ purchasesDtlPK=" + purchasesDtlPK + " ]";
    }
    
}
