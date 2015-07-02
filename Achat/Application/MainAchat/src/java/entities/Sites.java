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
import javax.persistence.Id;
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
@Table(name = "SITES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sites.findAll", query = "SELECT s FROM Sites s"),
    @NamedQuery(name = "Sites.findBySiteid", query = "SELECT s FROM Sites s WHERE s.siteid = :siteid"),
    @NamedQuery(name = "Sites.findByDescription", query = "SELECT s FROM Sites s WHERE s.description = :description")})
public class Sites implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "SITEID")
    private String siteid;
    @Size(max = 20)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sites")
    private Collection<StkPrd> stkPrdCollection;
    @OneToMany(mappedBy = "siteid")
    private Collection<Purchases> purchasesCollection;
    @OneToMany(mappedBy = "siteid")
    private Collection<Recept> receptCollection;

    public Sites() {
    }

    public Sites(String siteid) {
        this.siteid = siteid;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<StkPrd> getStkPrdCollection() {
        return stkPrdCollection;
    }

    public void setStkPrdCollection(Collection<StkPrd> stkPrdCollection) {
        this.stkPrdCollection = stkPrdCollection;
    }

    @XmlTransient
    public Collection<Purchases> getPurchasesCollection() {
        return purchasesCollection;
    }

    public void setPurchasesCollection(Collection<Purchases> purchasesCollection) {
        this.purchasesCollection = purchasesCollection;
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
        hash += (siteid != null ? siteid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sites)) {
            return false;
        }
        Sites other = (Sites) object;
        if ((this.siteid == null && other.siteid != null) || (this.siteid != null && !this.siteid.equals(other.siteid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Sites[ siteid=" + siteid + " ]";
    }
    
}
