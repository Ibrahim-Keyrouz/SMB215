/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.GroupsAchat;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author oracle
 */
@Stateless
public class GroupsAchatFacade extends AbstractFacade<GroupsAchat> {
    @PersistenceContext(unitName = "MainAchatPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
         
        return em;
    }

    public GroupsAchatFacade() {
        super(GroupsAchat.class);
         
    }
    
}
