/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.PurchasesDtl;
import entities.PurchasesDtlPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Path("entities.purchasesdtl")
public class PurchasesDtlFacadeREST extends AbstractFacade<PurchasesDtl> {
    @PersistenceContext(unitName = "STK_PRD_WSPU")
    private EntityManager em;

    private PurchasesDtlPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;docid=docidValue;barcode=barcodeValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entities.PurchasesDtlPK key = new entities.PurchasesDtlPK();
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
    
    
    
     

    public PurchasesDtlFacadeREST() {
        super(PurchasesDtl.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(PurchasesDtl entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, PurchasesDtl entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entities.PurchasesDtlPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public PurchasesDtl find(@PathParam("id") PathSegment id) {
        
        entities.PurchasesDtlPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces( "application/json")
    public List<PurchasesDtl> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<PurchasesDtl> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
}
