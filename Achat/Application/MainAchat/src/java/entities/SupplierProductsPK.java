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
public class SupplierProductsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "BARCODE")
    private String barcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "SUPPLIERID")
    private String supplierid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "USERID")
    private String userid;

    public SupplierProductsPK() {
    }

    public SupplierProductsPK(String barcode, String supplierid, String userid) {
        this.barcode = barcode;
        this.supplierid = supplierid;
        this.userid = userid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (barcode != null ? barcode.hashCode() : 0);
        hash += (supplierid != null ? supplierid.hashCode() : 0);
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupplierProductsPK)) {
            return false;
        }
        SupplierProductsPK other = (SupplierProductsPK) object;
        if ((this.barcode == null && other.barcode != null) || (this.barcode != null && !this.barcode.equals(other.barcode))) {
            return false;
        }
        if ((this.supplierid == null && other.supplierid != null) || (this.supplierid != null && !this.supplierid.equals(other.supplierid))) {
            return false;
        }
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SupplierProductsPK[ barcode=" + barcode + ", supplierid=" + supplierid + ", userid=" + userid + " ]";
    }
    
}
