/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author oracle
 */

  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author oracle
 */
@WebServlet(name = "Report_With_Params", urlPatterns = {"/Report_Params"})
public class Report_With_Params extends HttpServlet {
private Connection conn = null;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
    String reportName = request.getParameter("name");
    String vtheId_param = request.getParameter("docid");
   
    
    File reportFile = new File(getServletConfig().getServletContext().getRealPath("/reports/"+reportName+".jasper"));
    ServletOutputStream servletOutputStream = response.getOutputStream();
    byte[] bytes = null;
    Map<String,Object> parameter = new HashMap<String,Object>();
    if (reportName.contains("purchases")){
    parameter.put("cond", " and purchases.docid='"+vtheId_param+"'");
    }
    else{
        parameter.put("cond", " and recept.docid='"+vtheId_param+"'");
    }
       String HOST = "jdbc:oracle:thin:@localhost:1521:XE";
        String USERNAME = "hr";
        String PASSWORD = "remoteusers";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
    try
    {
        String reportPath =JasperRunManager.runReportToHtmlFile(reportFile.getPath(), null);
         File reportHtmlFile = new File(reportPath);
        bytes = JasperRunManager.runReportToHtmlFile(reportFile.getPath(),parameter,conn).getBytes();
        FileInputStream fis = new FileInputStream(reportHtmlFile);
         bytes =  new byte[(int)reportHtmlFile.length()];
        fis.read(bytes);
         response.setHeader("Content-Disposition","inline; filename=bob.html");
        response.setContentType("text/html");
        response.setContentLength(bytes.length);
        servletOutputStream.write(bytes, 0, bytes.length);
        servletOutputStream.flush();
        servletOutputStream.close();
        
       
        
       
    }
    catch (JRException e)
    {
        System.out.println(e);
    }
}
   
   public void initConnection() {

        String HOST = "jdbc:oracle:thin:@localhost:1521:XE";
        String USERNAME = "hr";
        String PASSWORD = "remoteusers";
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
  

