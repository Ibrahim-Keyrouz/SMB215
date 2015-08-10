<%-- 
    Document   : test
    Created on : Jan 31, 2015, 12:03:19 AM
    Author     : bk-laptop
--%>


<%@page import="sessions.UsersAchatFacade"%>
<%@page import="java.util.List"%>
<%@page import="entities.UsersAchat"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
     
        
       <jsp:useBean id="bob" class="beans.Bean_Page" />
      
        
        <%
 
      
      int mmbrexist = bob.getEsm();
        //    int mmbrexist = 8;
 
            if (mmbrexist > 0 ) { 
                 String redirectURL ="/MainAchat/faces/jsfpages/sites/List.xhtml";   
                 response.sendRedirect(redirectURL);
            } else {
               String redirectURL = "/MainAchat/faces/CreatingAdmin.xhtml";
                // String redirectURL ="/MainAchat/faces/jsfpages/category/List.xhtml";   
               response.sendRedirect(redirectURL);
            }
      

%>
        
    </body>
</html>
