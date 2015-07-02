<%-- 
    Document   : test
    Created on : Jan 31, 2015, 12:03:19 AM
    Author     : bk-laptop
--%>

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
        
        <%
    //
   
      int mmbrexist = 0;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hr", "remoteusers");
        Statement stmt = null;
        String query = "select USERID  from users_achat";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                mmbrexist++;

            }
            // >
            if (mmbrexist > 0) { 
                 String redirectURL ="/MainAchat/faces/jsfpages/category/List.xhtml";   
                 response.sendRedirect(redirectURL);
            } else {
               String redirectURL = "/MainAchat/faces/CreatingAdmin.xhtml";
                // String redirectURL ="/MainAchat/faces/jsfpages/category/List.xhtml";   
               response.sendRedirect(redirectURL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
                connection.close();
            }
        }

    
    
    
%>
        
    </body>
</html>
