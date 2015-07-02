/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Category;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author oracle
 */
@Stateless
public class CategoryFacade extends AbstractFacade<Category> {
    @PersistenceContext(unitName = "MainAchatPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        // em.getEntityManagerFactory().getCache().evictAll();
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
         
    }
    
}
