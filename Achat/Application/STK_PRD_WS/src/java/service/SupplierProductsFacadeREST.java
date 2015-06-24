/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.SupplierProducts;
import entities.SupplierProductsPK;
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
@Path("entities.supplierproducts")
public class SupplierProductsFacadeREST extends AbstractFacade<SupplierProducts> {
    @PersistenceContext(unitName = "STK_PRD_WSPU")
    private EntityManager em;

    private SupplierProductsPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;barcode=barcodeValue;supplierid=supplieridValue;userid=useridValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entities.SupplierProductsPK key = new entities.SupplierProductsPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> barcode = map.get("barcode");
        if (barcode != null && !barcode.isEmpty()) {
            key.setBarcode(barcode.get(0));
        }
        java.util.List<String> supplierid = map.get("supplierid");
        if (supplierid != null && !supplierid.isEmpty()) {
            key.setSupplierid(supplierid.get(0));
        }
        java.util.List<String> userid = map.get("userid");
        if (userid != null && !userid.isEmpty()) {
            key.setUserid(userid.get(0));
        }
        return key;
    }

    public SupplierProductsFacadeREST() {
        super(SupplierProducts.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(SupplierProducts entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, SupplierProducts entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entities.SupplierProductsPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public SupplierProducts find(@PathParam("id") PathSegment id) {
        entities.SupplierProductsPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<SupplierProducts> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<SupplierProducts> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
