/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//import static org.apache.poi.hssf.usermodel.HeaderFooter.page;

/**
 *
 * @author bk-laptop
 */
@ManagedBean(name = "mngsession")
@SessionScoped
public class Session implements Serializable {

    private String userid;
    private String password;
    HttpServletRequest request;

    FacesContext fc = FacesContext.getCurrentInstance();
    ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();

    /**
     * Creates a new instance of Session
     */
    public Session() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() throws ServletException {

        if (this.userid.isEmpty()) {
            FacesMessage message = new FacesMessage("Please enter a user !!!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext.getCurrentInstance().addMessage("form-login:usernameInput", message);

            return null;
        }

        if (this.password.isEmpty()) {
            FacesMessage message = new FacesMessage("Please enter a password !!!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("form-login:usernameInput", message);
            return null;
        }

        //context.addMessage(null,new FacesMessage("Ok"));
        FacesContext context = FacesContext.getCurrentInstance();
        request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(this.userid, this.password);
        } catch (ServletException e) {
            System.out.println(e);
            FacesMessage message = new FacesMessage("Login Failed !!!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext.getCurrentInstance().addMessage("form-login:usernameInput", message);

            return null;
        }

        return "/products/List";

    }
 
 public String logout() {
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    session.invalidate();
    return "/Login?faces-redirect=true"; }

    public void methodInManagedBean() throws IOException, ClassNotFoundException, SQLException {
        int mmbrexist = 0;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hr", "remoteusers");
        Statement stmt = null;
        String query = "select USERID  from users";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                mmbrexist++;

            }
            // >
            if (mmbrexist > 0) { 
                nav.performNavigation("/Login.xhtml");
            } else {
                nav.performNavigation("/CreatingAdmin.xhtml");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
                connection.close();
            }
        }

     //////////////////////////+
    }

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
     
 

}
