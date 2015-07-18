/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@SequenceGenerator(name="SUPPLIERSEQ", sequenceName="SUPPLIERSEQ", initialValue=1, allocationSize=1)
@Cacheable(false)
@Table(name = "SUPPLIER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Supplier.findAll", query = "SELECT s FROM Supplier s"),
    @NamedQuery(name = "Supplier.findBySupplierid", query = "SELECT s FROM Supplier s WHERE s.supplierid = :supplierid"),
    @NamedQuery(name = "Supplier.findByDescription", query = "SELECT s FROM Supplier s WHERE s.description = :description"),
    @NamedQuery(name = "Supplier.findByTel", query = "SELECT s FROM Supplier s WHERE s.tel = :tel"),
    @NamedQuery(name = "Supplier.findByEmail", query = "SELECT s FROM Supplier s WHERE s.email = :email"),
    @NamedQuery(name = "Supplier.findByStatus", query = "SELECT s FROM Supplier s WHERE s.status = :status"),
    @NamedQuery(name = "Supplier.findByAddress", query = "SELECT s FROM Supplier s WHERE s.address = :address")})
public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "SUPPLIERID")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="SUPPLIERSEQ")
    private Integer supplierid;

    
    @Size(min = 1, max = 20)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 20)
    @Column(name = "TEL")
    private String tel;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 100)
    @Column(name = "ADDRESS")
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier")
    private Collection<SupplierProducts> supplierProductsCollection;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne
    private UsersAchat userid;
    @OneToMany(mappedBy = "supplierid")
    private Collection<Purchases> purchasesCollection;

    public Supplier() {
    }

    public Supplier(Integer supplierid) {
        this.supplierid = supplierid;
    }

    public Supplier(Integer supplierid, String description, String status) {
        this.supplierid = supplierid;
        this.description = description;
        this.status = status;
    }

    public Integer getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Integer supplierid) {
        this.supplierid = supplierid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient
    public Collection<SupplierProducts> getSupplierProductsCollection() {
        return supplierProductsCollection;
    }

    public void setSupplierProductsCollection(Collection<SupplierProducts> supplierProductsCollection) {
        this.supplierProductsCollection = supplierProductsCollection;
    }

    public UsersAchat getUserid() {
        return userid;
    }

    public void setUserid(UsersAchat userid) {
        this.userid = userid;
    }

    @XmlTransient
    public Collection<Purchases> getPurchasesCollection() {
        return purchasesCollection;
    }

    public void setPurchasesCollection(Collection<Purchases> purchasesCollection) {
        this.purchasesCollection = purchasesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplierid != null ? supplierid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) object;
        if ((this.supplierid == null && other.supplierid != null) || (this.supplierid != null && !this.supplierid.equals(other.supplierid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Supplier[ supplierid=" + supplierid + " ]";
    }
    
}
