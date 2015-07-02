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
@Table(name = "GROUPS_ACHAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupsAchat.findAll", query = "SELECT g FROM GroupsAchat g"),
    @NamedQuery(name = "GroupsAchat.findByGroupid", query = "SELECT g FROM GroupsAchat g WHERE g.groupid = :groupid"),
    @NamedQuery(name = "GroupsAchat.findByDescription", query = "SELECT g FROM GroupsAchat g WHERE g.description = :description")})
public class GroupsAchat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "GROUPID")
    private Short groupid;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "description")
    private Collection<UsersAchat> usersAchatCollection;

    public GroupsAchat() {
    }

    public GroupsAchat(String description) {
        this.description = description;
    }

    public Short getGroupid() {
        return groupid;
    }

    public void setGroupid(Short groupid) {
        this.groupid = groupid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<UsersAchat> getUsersAchatCollection() {
        return usersAchatCollection;
    }

    public void setUsersAchatCollection(Collection<UsersAchat> usersAchatCollection) {
        this.usersAchatCollection = usersAchatCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (description != null ? description.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupsAchat)) {
            return false;
        }
        GroupsAchat other = (GroupsAchat) object;
        if ((this.description == null && other.description != null) || (this.description != null && !this.description.equals(other.description))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.GroupsAchat[ description=" + description + " ]";
    }
    
}
