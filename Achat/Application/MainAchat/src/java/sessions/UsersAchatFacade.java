/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.UsersAchat;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author oracle
 */
@Stateless
public class UsersAchatFacade extends AbstractFacade<UsersAchat> {
    @PersistenceContext(unitName = "MainAchatPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
         
        return em;
    }

    public UsersAchatFacade() {
        super(UsersAchat.class);
         
    }
    
}
