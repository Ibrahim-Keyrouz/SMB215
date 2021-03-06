/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author oracle
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.CategoryFacadeREST.class);
        resources.add(service.ProductFacadeREST.class);
        resources.add(service.PurchasesDtlFacadeREST.class);
        resources.add(service.PurchasesFacadeREST.class);
        resources.add(service.ReceptDtlFacadeREST.class);
        resources.add(service.ReceptFacadeREST.class);
        resources.add(service.SitesFacadeREST.class);
        resources.add(service.StkPrdFacadeREST.class);
        resources.add(service.SupplierFacadeREST.class);
        resources.add(service.SupplierProductsFacadeREST.class);
        resources.add(service.UsersAchatFacadeREST.class);
    }
    
}
