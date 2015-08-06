/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.faces.context.FacesContext;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
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
   protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JRException, AddressException, MessagingException
{
    String reportName = request.getParameter("name");
    String vtheId_param = request.getParameter("docid");
    String docType = request.getParameter("doctype");
    
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
    if (docType.contains("1")) { 
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
    
    }else if (docType.contains("2")) {
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), parameter, conn);

            List l = jasperPrint.getPages();
            if (l.size() != 0) {

            }

            JasperPrintManager.printReport(jasperPrint, false);
        
    }else {
            this.send(reportFile, reportName,parameter);

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
   
   
   
   
   public void send(File a, String b,Map<String,Object> c) throws NoSuchProviderException, AddressException, MessagingException, JRException {
        javax.mail.Session session = null;
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");

        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("cnamachat@gmail.com", "projetsmb215");
            }
        });
        session.setDebug(true);

        Transport transport = session.getTransport();
        InternetAddress addressFrom = new InternetAddress("cnamachat@gmail.com");
        MimeMessage message = new MimeMessage(session);
        message.setSender(addressFrom);

//
        // This HTML mail have to 2 part, the BODY and the embedded attachment
        //
        MimeMultipart multipart = new MimeMultipart("related");

        // first part  (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<H1>Hello</H1>";
        messageBodyPart.setContent(htmlText, "text/html");

        // add it
        multipart.addBodyPart(messageBodyPart);

        // second part (the image)
        messageBodyPart = new MimeBodyPart();
        messageBodyPart.setHeader("Content-Disposition", "attachment; filename=\""+ "CNAM " + b + "\"");
        byte[] bytes = null;

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

        JasperPrint jasperPrint = JasperFillManager.fillReport(a.getPath(), c, conn);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
       
        DataSource aAttachment = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
       
        messageBodyPart.setDataHandler(new DataHandler(aAttachment));

        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);
        

        message.setSubject("CNAM " + b);

        message.setRecipient(Message.RecipientType.TO, new InternetAddress("bob.keyrouz@gmail.com"));
        transport.connect();
        transport.send(message);
        transport.close();
        session = null;

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
    try {
        processRequest(request, response);
    } catch (JRException ex) {
        Logger.getLogger(Report_With_Params.class.getName()).log(Level.SEVERE, null, ex);
    } catch (MessagingException ex) {
        Logger.getLogger(Report_With_Params.class.getName()).log(Level.SEVERE, null, ex);
    }
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
    try {
        processRequest(request, response);
    } catch (JRException ex) {
        Logger.getLogger(Report_With_Params.class.getName()).log(Level.SEVERE, null, ex);
    } catch (MessagingException ex) {
        Logger.getLogger(Report_With_Params.class.getName()).log(Level.SEVERE, null, ex);
    }
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
  
