/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import beans.Session;
import entities.PurchasesDtl;
import entities.ReceptDtl;
import entities.Sites;
import entities.UsersAchat;
import java.util.List;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author oracle
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;
    
    @ManagedProperty(value="#{mngsession}") 
    Session session1;

    public Session getSession1() {
        return session1;
    }

    public void setSession1(Session session1) {
        this.session1 = session1;
    }

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
       //  getEntityManager().getEntityManagerFactory().getCache().evictAll();
        
     //   getEntityManagerFactory().getCache().evictAll();
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
      //   getEntityManager().getEntityManagerFactory().getCache().evictAll();
        
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
     //    getEntityManager().getEntityManagerFactory().getCache().evictAll();
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
       //  getEntityManager().getEntityManagerFactory().getCache().evictAll();
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
         getEntityManager().getEntityManagerFactory().getCache().evictAll();
       // getEntityManager().getEntityManagerFactory().getCache().evictAll();
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
     //    getEntityManager().getEntityManagerFactory().getCache().evictAll();
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
     //   getEntityManager().getEntityManagerFactory().getCache().evictAll();
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    
    public List<UsersAchat> find_user_session(){
         HttpServletRequest request;
         
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        CriteriaQuery<UsersAchat> cq = cb.createQuery(UsersAchat.class);
        Metamodel m = getEntityManager().getMetamodel();
        EntityType<UsersAchat> pd = m.entity(UsersAchat.class);
        Root<UsersAchat> rpd = cq.from(UsersAchat.class); 
        FacesContext context = FacesContext.getCurrentInstance();
        request = (HttpServletRequest) context.getExternalContext().getRequest();
        
       cq.where(cb.equal(rpd.get("userid"),request.getUserPrincipal()));
       
      
      
      //  cq.where(cb.like(rpd.get("recept").<String>get("docid"),id+"%"));
      
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public List<Sites> find_site_session(String a){
        
        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        CriteriaQuery<Sites> cq = cb.createQuery(Sites.class);
        Metamodel m = getEntityManager().getMetamodel();
        EntityType<Sites> pd = m.entity(Sites.class);
        Root<Sites> rpd = cq.from(Sites.class); 
       
        
       //  a = this.getSession1().getChoosesiteid().toString();
       cq.where(cb.equal(rpd.get("siteid"),a));
       
      
      
      //  cq.where(cb.like(rpd.get("recept").<String>get("docid"),id+"%"));
      
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public List<PurchasesDtl> find_item_purchase(String a){
        
        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        CriteriaQuery<PurchasesDtl> cq = cb.createQuery(PurchasesDtl.class);
        Metamodel m = getEntityManager().getMetamodel();
        EntityType<PurchasesDtl> pd = m.entity(PurchasesDtl.class);
        Root<PurchasesDtl> rpd = cq.from(PurchasesDtl.class); 
       
        
       //  a = this.getSession1().getChoosesiteid().toString();
       //cq.where(cb.equal(rpd.get("docid"),a));
       
       cq.where(cb.equal(rpd.get("purchases").get("docid"),a));
       
      
      
      //  cq.where(cb.like(rpd.get("recept").<String>get("docid"),id+"%"));
      
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    
    
     public List<ReceptDtl> find_item_recept(String a){
        
        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        CriteriaQuery<ReceptDtl> cq = cb.createQuery(ReceptDtl.class);
        Metamodel m = getEntityManager().getMetamodel();
        EntityType<ReceptDtl> pd = m.entity(ReceptDtl.class);
        Root<ReceptDtl> rpd = cq.from(ReceptDtl.class); 
       
         cq.where(cb.like(rpd.get("recept").<String>get("docid"),a));
      
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    
}
