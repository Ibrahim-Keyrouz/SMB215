/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author oracle
 */
@Embeddable
public class StkPrdPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "SITEID")
    private String siteid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "BARCODE")
    private String barcode;

    public StkPrdPK() {
    }

    public StkPrdPK(String siteid, String barcode) {
        this.siteid = siteid;
        this.barcode = barcode;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (siteid != null ? siteid.hashCode() : 0);
        hash += (barcode != null ? barcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StkPrdPK)) {
            return false;
        }
        StkPrdPK other = (StkPrdPK) object;
        if ((this.siteid == null && other.siteid != null) || (this.siteid != null && !this.siteid.equals(other.siteid))) {
            return false;
        }
        if ((this.barcode == null && other.barcode != null) || (this.barcode != null && !this.barcode.equals(other.barcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.StkPrdPK[ siteid=" + siteid + ", barcode=" + barcode + " ]";
    }
    
}
