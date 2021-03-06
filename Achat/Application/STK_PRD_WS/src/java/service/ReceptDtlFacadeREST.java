/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.ReceptDtl;
import entities.ReceptDtlPK;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author oracle
 */
@Stateless
@Path("entities.receptdtl")
public class ReceptDtlFacadeREST extends AbstractFacade<ReceptDtl> {
    @PersistenceContext(unitName = "STK_PRD_WSPU")
    private EntityManager em;

    private ReceptDtlPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;docid=docidValue;barcode=barcodeValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entities.ReceptDtlPK key = new entities.ReceptDtlPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> docid = map.get("docid");
        if (docid != null && !docid.isEmpty()) {
            key.setDocid(docid.get(0));
        }
        java.util.List<String> barcode = map.get("barcode");
        if (barcode != null && !barcode.isEmpty()) {
            key.setBarcode(barcode.get(0));
        }
        return key;
    }

    public ReceptDtlFacadeREST() {
        super(ReceptDtl.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ReceptDtl entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, ReceptDtl entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entities.ReceptDtlPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public ReceptDtl find(@PathParam("id") PathSegment id) {
        entities.ReceptDtlPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({ "application/json"})
    public List<ReceptDtl> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ReceptDtl> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
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
    
    
     @POST
    @Path("insrt_receptdtl")
    @Consumes({ "application/json"})
    //public void insert_array(List<Cars> entity) {
     //   super.insert_array( entity);
    public void insert_array(List<ReceptDtl> entity) {
        em.getEntityManagerFactory().getCache().evictAll();
       // Cars a = entity[0];
       // super.create(a);
        Collection<ReceptDtl> entities = entity;
        
             for (ReceptDtl c : entities)   {
                ReceptDtl a = null;
               
                             
                    a = super.find(c.getReceptDtlPK());
                
                
               if (a == null){
                   create(c);
                    
        }
                else{
                    
                  // Here we are editing the ReceptDtl for a reason that the Supplier may come multiple times for the same PurchaseID
                   //But we need to take caution of the saved Products in the receptDTL for the same PurchaseID not to be scanned another time.
                    
                c.setQty(c.getQty().add(a.getQty()));
                     edit(c);
                    
                 }
             }
       }
    
    
    
    @GET
    @Path("/details/{id}")
    @Produces({"application/json"})
    public List<ReceptDtl> find_recept_details(@PathParam("id") PathSegment id) {
         em.getEntityManagerFactory().getCache().evictAll();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        CriteriaQuery<ReceptDtl> cq = cb.createQuery(ReceptDtl.class);
        Metamodel m = em.getMetamodel();
        EntityType<ReceptDtl> pd = m.entity(ReceptDtl.class);
        Root<ReceptDtl> rpd = cq.from(ReceptDtl.class);        
       // cq.where(cb.equal(rpd.get("recept").get("docid"),id));
      
        cq.where(cb.like(rpd.get("recept").<String>get("docid"),id+"%"));
        
        return em.createQuery(cq).getResultList();        
             
        
    }
    
}
