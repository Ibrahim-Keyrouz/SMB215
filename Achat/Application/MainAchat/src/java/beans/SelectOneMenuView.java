/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author oracle
 */
@ManagedBean(name = "selectonemenuview")

public class SelectOneMenuView {
     HttpServletRequest request;

    private String status;
    private List<SelectItem> statuses;
    
    private String tva;
    private List<SelectItem> tvas;

    private String done;
    private List<SelectItem> dones;
    
    private String userid;
    private List<SelectItem> userids;
   
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
        
        
        //user session
         SelectItemGroup g4 = new SelectItemGroup("User Session");
         FacesContext context = FacesContext.getCurrentInstance();
        request = (HttpServletRequest) context.getExternalContext().getRequest();
        g4.setSelectItems(new SelectItem[]{new SelectItem(request.getRemoteUser(), request.getRemoteUser())});
        userids = new ArrayList<>();
        userids.add(g4);

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
    
    
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    

    public List<SelectItem> getUserids() {
        return userids;
    }

}
