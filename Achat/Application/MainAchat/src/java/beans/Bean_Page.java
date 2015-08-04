/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.UsersAchat;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 *
 * @author oracle
 */
public class Bean_Page {
     int esm;
      @PersistenceContext(unitName = "MainAchatPU")
          EntityManager em = null;

    public int getEsm() {
        esm = count_Users();
        return esm;
    }

    public void setEsm(int esm) {
        this.esm = esm;
    }

            
       public int count_Users() {
         //  @PersistenceContext(unitName = "MainAchatPU")
          
           EntityManagerFactory emf = Persistence.createEntityManagerFactory("MainAchatPU");
           em = emf.createEntityManager();
     //   getEntityManager().getEntityManagerFactory().getCache().evictAll();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<UsersAchat> rt = cq.from(UsersAchat.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

   
}
