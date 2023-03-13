package pl.polsl.tomasz.michalik.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import pl.polsl.tomasz.michalik.model.TuringMachine;
import pl.polsl.tomasz.michalik.utils.HTMLView;
import pl.polsl.tomasz.michalik.utils.TMReader;

/**
 * Servlet loading the machine
 *
 * @author Tomasz Michalik
 * @version 1.0
 */
@WebServlet("/LoadMachine")
public class LoadMachine extends HttpServlet {

    private HTMLView view;

    /**
     * constructor
     */
    public LoadMachine() {
        view = new HTMLView(9);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       
        TMReader tmr = new TMReader();
        try {
            
            // loading the JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            //establishing the connection 
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/turingMachine", "tm", "tm");
        

            TuringMachine tm = tmr.readTMFromString(request.getParameter("configuration"));
            Statement statement = con.createStatement();
            statement.execute("insert into tms values(1)");
            
            int i=1;
            for (String transition: tm.getTransitionsStrings()){
                statement.execute("insert into transitions values( "+ i++ + ", '" + transition + "', 1)");
            }
            
            HttpSession session = request.getSession();
            session.setAttribute("turingmachine", tm);
            session.setAttribute("nextConfigId", 1);
            session.setAttribute("connection", con);

            //clearing cookies
            for (Cookie cookie : request.getCookies()) {
                cookie.setMaxAge(0);
                cookie.setValue("");
                cookie.setPath("/");
            }

            
            Cookie cookie = new Cookie("noSteps", "0");
            response.addCookie(cookie);
            view.showMachine(request, response);

        } catch (Exception ex) {
            view.handleException(ex.getMessage(), response);
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
