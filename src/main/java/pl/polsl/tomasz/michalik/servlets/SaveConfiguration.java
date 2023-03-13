package pl.polsl.tomasz.michalik.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;
import pl.polsl.tomasz.michalik.model.Tape;
import pl.polsl.tomasz.michalik.model.TuringMachine;
import pl.polsl.tomasz.michalik.utils.HTMLView;
/**
 * Saves the state of the machine into the database
 *
 * @author Tomasz Michalik
 * @version 1.0
 */
@WebServlet(name = "SaveConfiguration", urlPatterns = {"/SaveConfiguration"})
public class SaveConfiguration extends HttpServlet {
    
    private HTMLView view;
    
    public SaveConfiguration(){
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
        response.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession();
            TuringMachine tm = (TuringMachine) session.getAttribute("turingmachine");
            
            int configID = (int) session.getAttribute("nextConfigId");
            session.setAttribute("nextConfigId", configID+1);
            
            int noSteps = 0;
            for (Cookie c: request.getCookies()){
                if (c.getName().equals("noSteps")){
                    noSteps = Integer.parseInt(c.getValue())+1;
                }
            }
            
            Connection con = (Connection) session.getAttribute("connection");
            Statement statement = con.createStatement();
            statement.execute("insert into configurations values (" + configID + ", '" + tm.getCurrentState() + "', " + noSteps + ", 1) ");
            ArrayList<Tape> tapes = tm.getTapes();
            for (int i=0; i<tapes.size(); i++){
                statement.execute("insert into tapes values (" + i + ", '" + tapes.get(i).toString() + "',"  + configID + ")");
            }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SaveConfiguration</title>");  
            out.println("<link rel=\"stylesheet\" href=\"turingMachine.css\">");
            out.println("</head>");
            out.println("<body><div>");
            out.println("<h1>the state of the machine has been saved to the database </h1>");
            out.println("you can now go to the previous page <br> below are the saved congigurations:<br><br>");
            
            
            
            ResultSet configs = statement.executeQuery("select * from configurations");
            ArrayList<String[]> conf = new ArrayList<>();
            while (configs.next()){
                String[] s = new String[2];
                s[0] = configs.getString("id");
                s[1] = configs.getString("current_state");
                conf.add(s);
            }
            configs.close();
            
            out.println("<ul>");
            for (String[] s: conf){
                out.println("<li> congiguration no: " +  s[0] + ", current state: "+s[1]);
                out.println("<br><br><ol>");
                
                ResultSet configTapes = statement.executeQuery("select * from tapes where config = " + s[0]);
                while (configTapes.next()){
                    out.println("<li> " + configTapes.getString("contents") + "</li>");
                }
                configTapes.close();
                out.println("</ol></li>" );
            }
            out.println("<br></ul>");
            
            
            out.println("</div></body>");
            out.println("</html>");
        } catch (SQLException ex) {
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
