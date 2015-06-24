/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author oracle
 */
@Entity
@Cacheable(false)
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByBarcode", query = "SELECT p FROM Product p WHERE p.barcode = :barcode"),
    @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
    @NamedQuery(name = "Product.findByTva", query = "SELECT p FROM Product p WHERE p.tva = :tva"),
    @NamedQuery(name = "Product.findByTvaType", query = "SELECT p FROM Product p WHERE p.tvaType = :tvaType"),
    @NamedQuery(name = "Product.findByStatus", query = "SELECT p FROM Product p WHERE p.status = :status")})
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "BARCODE")
    private String barcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "DESCRIPTION")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "TVA")
    private BigDecimal tva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TVA_TYPE")
    private String tvaType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "STATUS")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<StkPrd> stkPrdCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<SupplierProducts> supplierProductsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<ReceptDtl> receptDtlCollection;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne
    private UsersAchat userid;
    @JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY")
    @ManyToOne
    private Category category;

    public Product() {
    }

    public Product(String barcode) {
        this.barcode = barcode;
    }

    public Product(String barcode, String description, BigDecimal tva, String tvaType, String status) {
        this.barcode = barcode;
        this.description = description;
        this.tva = tva;
        this.tvaType = tvaType;
        this.status = status;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public String getTvaType() {
        return tvaType;
    }

    public void setTvaType(String tvaType) {
        this.tvaType = tvaType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<StkPrd> getStkPrdCollection() {
        return stkPrdCollection;
    }

    public void setStkPrdCollection(Collection<StkPrd> stkPrdCollection) {
        this.stkPrdCollection = stkPrdCollection;
    }

    @XmlTransient
    public Collection<SupplierProducts> getSupplierProductsCollection() {
        return supplierProductsCollection;
    }

    public void setSupplierProductsCollection(Collection<SupplierProducts> supplierProductsCollection) {
        this.supplierProductsCollection = supplierProductsCollection;
    }

    @XmlTransient
    public Collection<ReceptDtl> getReceptDtlCollection() {
        return receptDtlCollection;
    }

    public void setReceptDtlCollection(Collection<ReceptDtl> receptDtlCollection) {
        this.receptDtlCollection = receptDtlCollection;
    }

    public UsersAchat getUserid() {
        return userid;
    }

    public void setUserid(UsersAchat userid) {
        this.userid = userid;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (barcode != null ? barcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.barcode == null && other.barcode != null) || (this.barcode != null && !this.barcode.equals(other.barcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Product[ barcode=" + barcode + " ]";
    }
    
}
