/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.UsersAchat;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author oracle
 */
@Stateless
@Path("entities.usersachat")
public class UsersAchatFacadeREST extends AbstractFacade<UsersAchat> {
    @PersistenceContext(unitName = "STK_PRD_WSPU")
    private EntityManager em;

    public UsersAchatFacadeREST() {
        super(UsersAchat.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(UsersAchat entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, UsersAchat entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public UsersAchat find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<UsersAchat> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<UsersAchat> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    
    @GET
    @Path("/email_exist/{email}")
    @Produces({"application/json"})
    public List<UsersAchat> find_user_session(@PathParam("email") String email){
         em.getEntityManagerFactory().getCache().evictAll();
        CriteriaBuilder cb = em.getCriteriaBuilder();
               
        CriteriaQuery<UsersAchat> cq = cb.createQuery(UsersAchat.class);
        Metamodel m = em.getMetamodel();
        EntityType<UsersAchat> pd = m.entity(UsersAchat.class);
        Root<UsersAchat> rpd = cq.from(UsersAchat.class); 
       cq.where(cb.equal(rpd.get("email"),email));
       
      
      
      //  cq.where(cb.like(rpd.get("recept").<String>get("docid"),id+"%"));
      
     
        return em.createQuery(cq).getResultList();
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
