/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;


import entities.UsersAchat;
import java.security.MessageDigest;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    
     private String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);

        }
    }
    
        public String changePassword(String a,String b,String c) {
     

      //  em.getTransaction().begin();
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaUpdate<UsersAchat> updateCriteria = cb.createCriteriaUpdate(UsersAchat.class);
Root<UsersAchat> root = updateCriteria.from(UsersAchat.class);
// update dateOfBirth property
updateCriteria.set(root.get("password"), sha256(b));
// set where clause
System.out.println(a);
updateCriteria.where(cb.equal(root.get("userid"), a));
// update
int affected = em.createQuery(updateCriteria).executeUpdate();
HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "/Login?faces-redirect=true";

//em.getTransaction().commit();
    }
    
    
    
}
