/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Sites;
import java.io.Serializable;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import sessions.SitesFacade;
//import static org.apache.poi.hssf.usermodel.HeaderFooter.page;

/**
 *
 * @author bk-laptop
 */
@ManagedBean(name = "mngsession")
@SessionScoped
public class Session implements Serializable {
    private String choosesiteid ;
    private String userid;
    private String password;
    HttpServletRequest request;

    FacesContext fc = FacesContext.getCurrentInstance();
    ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
@EJB
    private sessions.SitesFacade ejbFacade;
    /**
     * Creates a new instance of Session
     */
    public Session() {
    }

    public String getUserid() {
        return userid;
    }

    private SitesFacade getFacade() {
        return ejbFacade;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getChoosesiteid() {
        return choosesiteid;
    }

    public void setChoosesiteid(String choosesiteid) {
        this.choosesiteid = choosesiteid;
    }
    
  

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

 
 public String logout() {
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    session.invalidate();
    return "/Login?faces-redirect=true"; }

   
    public void createAdmin() throws ClassNotFoundException, SQLException {
        try {
        this.setUserid("0001");
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hr", "remoteusers");
        Statement stmt = null;
        Number a ;
        a = 1 ;
         String query1 = "insert into groups (DESCRIPTION,groupid) values ('admin',"+a+")";
         a = 2 ;
        String query2 = "insert into groups (DESCRIPTION,groupid) values ('user',"+a+")";
       
        String query = "insert into users values ('"+this.getUserid() +"','admin','"+sha256(this.getPassword())+"','admin')";
        stmt = connection.createStatement();
         
         stmt.executeUpdate(query1);
         stmt.executeUpdate(query2);
         stmt.executeUpdate(query);
           nav.performNavigation("/faces/Login.xhtml");
        }catch(SQLException e) {
            e.printStackTrace();
             nav.performNavigation("/faces/error.xhtml");
        }
        
    }
    
     private String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            
            for (int i = 0 ; i <hash.length;i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1 ) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
            
            }
        }
     
     public String getUser() {
       // return request.getUserPrincipal().getName();
         FacesContext context = FacesContext.getCurrentInstance();
        request = (HttpServletRequest) context.getExternalContext().getRequest();
        return request.getRemoteUser();
        
     }
     
     
     public List<Sites> getItemAvailableSelectOneSession() {
        return getFacade().find_site_session(returnSitefromString(this.getChoosesiteid()));
    }
     
     
     public String returnSitefromString(String a) {
       return  a.substring(23, 25);
         
     }
     
     
     
   
     
   
     
 

}
