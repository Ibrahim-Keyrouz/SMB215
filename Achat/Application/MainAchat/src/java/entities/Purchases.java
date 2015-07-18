/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author oracle
 */
@Entity
@SequenceGenerator(name="PURCHASESEQ", sequenceName="PURCHASESEQ", initialValue=1, allocationSize=1)

@Cacheable(false)
@Table(name = "PURCHASES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Purchases.findAll", query = "SELECT p FROM Purchases p"),
    @NamedQuery(name = "Purchases.findByDocid", query = "SELECT p FROM Purchases p WHERE p.docid = :docid"),
    @NamedQuery(name = "Purchases.findByTrsdate", query = "SELECT p FROM Purchases p WHERE p.trsdate = :trsdate"),
    @NamedQuery(name = "Purchases.findByDone", query = "SELECT p FROM Purchases p WHERE p.done = :done")})
public class Purchases implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "DOCID")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="PURCHASESEQ")
    private Integer docid;
    @Column(name = "TRSDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trsdate;
    @Size(max = 1)
    @Column(name = "DONE")
    private String done;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchases")
    private Collection<PurchasesDtl> purchasesDtlCollection;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne
    private UsersAchat userid;
    @JoinColumn(name = "SUPPLIERID", referencedColumnName = "SUPPLIERID")
    @ManyToOne
    private Supplier supplierid;
    @JoinColumn(name = "SITEID", referencedColumnName = "SITEID")
    @ManyToOne
    private Sites siteid;
    @OneToMany(mappedBy = "relatedDocid")
    private Collection<Recept> receptCollection;

    public Purchases() {
    }

    public Purchases(Integer docid) {
        this.docid = docid;
    }

    public Integer getDocid() {
        return docid;
    }

    public void setDocid(Integer docid) {
        this.docid = docid;
    }

    public Date getTrsdate() {
        return trsdate;
    }

    public void setTrsdate(Date trsdate) {
        this.trsdate = trsdate;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    @XmlTransient
    public Collection<PurchasesDtl> getPurchasesDtlCollection() {
        return purchasesDtlCollection;
    }

    public void setPurchasesDtlCollection(Collection<PurchasesDtl> purchasesDtlCollection) {
        this.purchasesDtlCollection = purchasesDtlCollection;
    }

    public UsersAchat getUserid() {
        return userid;
    }

    public void setUserid(UsersAchat userid) {
        this.userid = userid;
    }

    public Supplier getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Supplier supplierid) {
        this.supplierid = supplierid;
    }

    public Sites getSiteid() {
        return siteid;
    }

    public void setSiteid(Sites siteid) {
        this.siteid = siteid;
    }

    @XmlTransient
    public Collection<Recept> getReceptCollection() {
        return receptCollection;
    }

    public void setReceptCollection(Collection<Recept> receptCollection) {
        this.receptCollection = receptCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docid != null ? docid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchases)) {
            return false;
        }
        Purchases other = (Purchases) object;
        if ((this.docid == null && other.docid != null) || (this.docid != null && !this.docid.equals(other.docid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Purchases[ docid=" + docid + " ]";
    }
    
}
