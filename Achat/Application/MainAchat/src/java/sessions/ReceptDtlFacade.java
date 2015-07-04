/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.ReceptDtl;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author oracle
 */
@Stateless
public class ReceptDtlFacade extends AbstractFacade<ReceptDtl> {
    @PersistenceContext(unitName = "MainAchatPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
         
        return em;
    }

    public ReceptDtlFacade() {
        super(ReceptDtl.class);
         
    }
    
      public void bob123() {
        em.getEntityManagerFactory().getCache().evictAll();
    }
    
}
