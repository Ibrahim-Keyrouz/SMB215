/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.security.MessageDigest;
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
@Table(name = "USERS_ACHAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersAchat.findAll", query = "SELECT u FROM UsersAchat u"),
    @NamedQuery(name = "UsersAchat.findByUserid", query = "SELECT u FROM UsersAchat u WHERE u.userid = :userid"),
    @NamedQuery(name = "UsersAchat.findByEmail", query = "SELECT u FROM UsersAchat u WHERE u.email = :email"),
    @NamedQuery(name = "UsersAchat.findByPassword", query = "SELECT u FROM UsersAchat u WHERE u.password = :password"),
    @NamedQuery(name = "UsersAchat.findByStatus", query = "SELECT u FROM UsersAchat u WHERE u.status = :status")})
public class UsersAchat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "USERID")
    private String userid;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 64)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "DESCRIPTION", referencedColumnName = "DESCRIPTION")
    @ManyToOne
    private GroupsAchat description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usersAchat")
    private Collection<SupplierProducts> supplierProductsCollection;
    @OneToMany(mappedBy = "userid")
    private Collection<Supplier> supplierCollection;
    @OneToMany(mappedBy = "userid")
    private Collection<Purchases> purchasesCollection;
    @OneToMany(mappedBy = "userid")
    private Collection<Product> productCollection;

    public UsersAchat() {
    }

    public UsersAchat(String userid) {
        this.userid = userid;
    }

    public UsersAchat(String userid, String status) {
        this.userid = userid;
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

  
    
    public void setPassword(String password) {
        
        
       this.password = this.sha256(password);
        //this.password = password;
    }

 
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GroupsAchat getDescription() {
        return description;
    }

    public void setDescription(GroupsAchat description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<SupplierProducts> getSupplierProductsCollection() {
        return supplierProductsCollection;
    }

    public void setSupplierProductsCollection(Collection<SupplierProducts> supplierProductsCollection) {
        this.supplierProductsCollection = supplierProductsCollection;
    }

    @XmlTransient
    public Collection<Supplier> getSupplierCollection() {
        return supplierCollection;
    }

    public void setSupplierCollection(Collection<Supplier> supplierCollection) {
        this.supplierCollection = supplierCollection;
    }

    @XmlTransient
    public Collection<Purchases> getPurchasesCollection() {
        return purchasesCollection;
    }

    public void setPurchasesCollection(Collection<Purchases> purchasesCollection) {
        this.purchasesCollection = purchasesCollection;
    }

    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersAchat)) {
            return false;
        }
        UsersAchat other = (UsersAchat) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UsersAchat[ userid=" + userid + " ]";
    }
    
    
        private String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            
            for (int i = 0 ; i <hash.length;i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1 ) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
            
            }
        }
    
}
