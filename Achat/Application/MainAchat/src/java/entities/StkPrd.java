/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
@Table(name = "STK_PRD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StkPrd.findAll", query = "SELECT s FROM StkPrd s"),
    @NamedQuery(name = "StkPrd.findBySiteid", query = "SELECT s FROM StkPrd s WHERE s.stkPrdPK.siteid = :siteid"),
    @NamedQuery(name = "StkPrd.findByBarcode", query = "SELECT s FROM StkPrd s WHERE s.stkPrdPK.barcode = :barcode"),
    @NamedQuery(name = "StkPrd.findByQty", query = "SELECT s FROM StkPrd s WHERE s.qty = :qty"),
    @NamedQuery(name = "StkPrd.findByQtyNotification", query = "SELECT s FROM StkPrd s WHERE s.qtyNotification = :qtyNotification")})
public class StkPrd implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StkPrdPK stkPrdPK;
    @Column(name = "QTY")
    private Integer qty;
    @Column(name = "QTY_NOTIFICATION")
    private Integer qtyNotification;
    @JoinColumn(name = "BARCODE", referencedColumnName = "BARCODE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @JoinColumn(name = "SITEID", referencedColumnName = "SITEID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sites sites;

    public StkPrd() {
    }

    public StkPrd(StkPrdPK stkPrdPK) {
        this.stkPrdPK = stkPrdPK;
    }

    public StkPrd(String siteid, String barcode) {
        this.stkPrdPK = new StkPrdPK(siteid, barcode);
    }

    public StkPrdPK getStkPrdPK() {
        return stkPrdPK;
    }

    public void setStkPrdPK(StkPrdPK stkPrdPK) {
        this.stkPrdPK = stkPrdPK;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getQtyNotification() {
        return qtyNotification;
    }

    public void setQtyNotification(Integer qtyNotification) {
        this.qtyNotification = qtyNotification;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sites getSites() {
        return sites;
    }

    public void setSites(Sites sites) {
        this.sites = sites;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stkPrdPK != null ? stkPrdPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StkPrd)) {
            return false;
        }
        StkPrd other = (StkPrd) object;
        if ((this.stkPrdPK == null && other.stkPrdPK != null) || (this.stkPrdPK != null && !this.stkPrdPK.equals(other.stkPrdPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.StkPrd[ stkPrdPK=" + stkPrdPK + " ]";
    }
    
}
