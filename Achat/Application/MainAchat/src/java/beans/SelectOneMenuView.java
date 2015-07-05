/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

/**
 *
 * @author oracle
 */
@ManagedBean(name = "selectonemenuview")

public class SelectOneMenuView {

    private String status;
    private List<SelectItem> statuses;
    
    private String tva;
    private List<SelectItem> tvas;

    private String done;
    private List<SelectItem> dones;
   
    public SelectOneMenuView(){
        
    }
    
    @PostConstruct
    public void init() {

        //Status
        SelectItemGroup g1 = new SelectItemGroup("Status");
        g1.setSelectItems(new SelectItem[]{new SelectItem("A", "Active"), new SelectItem("S", "Stopped")});

        statuses = new ArrayList<>();
        statuses.add(g1);
        
        //Tva Type
         SelectItemGroup g2 = new SelectItemGroup("Tva Type");
        g2.setSelectItems(new SelectItem[]{new SelectItem("A", "Amount"), new SelectItem("P", "Percentage")});

        tvas = new ArrayList<>();
        tvas.add(g2);
        
        //done
         SelectItemGroup g3 = new SelectItemGroup("Done ?");
        g3.setSelectItems(new SelectItem[]{new SelectItem("N", "NO"), new SelectItem("Y", "YES")});

        dones = new ArrayList<>();
        dones.add(g3);

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatuses() {
        return statuses;
    }
    
    
       public String getTva() {
        return tva;
    }

    public void setTva(String tva) {
        this.tva = tva;
    }

    public List<SelectItem> getTvas() {
        return tvas;
    }
    
    
    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public List<SelectItem> getDones() {
        return dones;
    }

}
