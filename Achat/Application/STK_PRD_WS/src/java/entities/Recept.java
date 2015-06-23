/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
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
@Cacheable(false)
@Table(name = "RECEPT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recept.findAll", query = "SELECT r FROM Recept r"),
    @NamedQuery(name = "Recept.findByDocid", query = "SELECT r FROM Recept r WHERE r.docid = :docid"),
    @NamedQuery(name = "Recept.findByTrsdate", query = "SELECT r FROM Recept r WHERE r.trsdate = :trsdate"),
    @NamedQuery(name = "Recept.findByInvoiceDiscount", query = "SELECT r FROM Recept r WHERE r.invoiceDiscount = :invoiceDiscount")})
public class Recept implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "DOCID")
    private String docid;
    @Column(name = "TRSDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trsdate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "INVOICE_DISCOUNT")
    private BigDecimal invoiceDiscount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recept")
    private Collection<ReceptDtl> receptDtlCollection;
    @JoinColumn(name = "SITEID", referencedColumnName = "SITEID")
    @ManyToOne
    private Sites siteid;
    @JoinColumn(name = "RELATED_DOCID", referencedColumnName = "DOCID")
    @ManyToOne
    private Purchases relatedDocid;

    public Recept() {
    }

    public Recept(String docid) {
        this.docid = docid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public Date getTrsdate() {
        return trsdate;
    }

    public void setTrsdate(Date trsdate) {
        this.trsdate = trsdate;
    }

    public BigDecimal getInvoiceDiscount() {
        return invoiceDiscount;
    }

    public void setInvoiceDiscount(BigDecimal invoiceDiscount) {
        this.invoiceDiscount = invoiceDiscount;
    }

    @XmlTransient
    public Collection<ReceptDtl> getReceptDtlCollection() {
        return receptDtlCollection;
    }

    public void setReceptDtlCollection(Collection<ReceptDtl> receptDtlCollection) {
        this.receptDtlCollection = receptDtlCollection;
    }

    public Sites getSiteid() {
        return siteid;
    }

    public void setSiteid(Sites siteid) {
        this.siteid = siteid;
    }

    public Purchases getRelatedDocid() {
        return relatedDocid;
    }

    public void setRelatedDocid(Purchases relatedDocid) {
        this.relatedDocid = relatedDocid;
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
        if (!(object instanceof Recept)) {
            return false;
        }
        Recept other = (Recept) object;
        if ((this.docid == null && other.docid != null) || (this.docid != null && !this.docid.equals(other.docid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Recept[ docid=" + docid + " ]";
    }
    
}
