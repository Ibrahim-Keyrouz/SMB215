/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Cacheable;
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
@Table(name = "SUPPLIER_PRODUCTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupplierProducts.findAll", query = "SELECT s FROM SupplierProducts s"),
    @NamedQuery(name = "SupplierProducts.findByBarcode", query = "SELECT s FROM SupplierProducts s WHERE s.supplierProductsPK.barcode = :barcode"),
    @NamedQuery(name = "SupplierProducts.findBySupplierid", query = "SELECT s FROM SupplierProducts s WHERE s.supplierProductsPK.supplierid = :supplierid"),
    @NamedQuery(name = "SupplierProducts.findByUserid", query = "SELECT s FROM SupplierProducts s WHERE s.supplierProductsPK.userid = :userid")})
public class SupplierProducts implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SupplierProductsPK supplierProductsPK;
    @JoinColumn(name = "BARCODE", referencedColumnName = "BARCODE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @JoinColumn(name = "SUPPLIERID", referencedColumnName = "SUPPLIERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Supplier supplier;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UsersAchat usersAchat;

    public SupplierProducts() {
    }

    public SupplierProducts(SupplierProductsPK supplierProductsPK) {
        this.supplierProductsPK = supplierProductsPK;
    }

    public SupplierProducts(String barcode, int supplierid, String userid) {
        this.supplierProductsPK = new SupplierProductsPK(barcode, supplierid, userid);
    }

    public SupplierProductsPK getSupplierProductsPK() {
        return supplierProductsPK;
    }

    public void setSupplierProductsPK(SupplierProductsPK supplierProductsPK) {
        this.supplierProductsPK = supplierProductsPK;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public UsersAchat getUsersAchat() {
        return usersAchat;
    }

    public void setUsersAchat(UsersAchat usersAchat) {
        this.usersAchat = usersAchat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplierProductsPK != null ? supplierProductsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupplierProducts)) {
            return false;
        }
        SupplierProducts other = (SupplierProducts) object;
        if ((this.supplierProductsPK == null && other.supplierProductsPK != null) || (this.supplierProductsPK != null && !this.supplierProductsPK.equals(other.supplierProductsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SupplierProducts[ supplierProductsPK=" + supplierProductsPK + " ]";
    }
    
}
