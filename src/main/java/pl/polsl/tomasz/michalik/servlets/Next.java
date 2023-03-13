package pl.polsl.tomasz.michalik.servlets;

import java.io.IOException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import pl.polsl.tomasz.michalik.exceptions.TMException;
import pl.polsl.tomasz.michalik.model.Move;
import pl.polsl.tomasz.michalik.model.Transition;
import pl.polsl.tomasz.michalik.model.TuringMachine;
import pl.polsl.tomasz.michalik.utils.HTMLView;

/**
 * servlet going to the next step of the machine
 *
 * @author Tomasz Michalik
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/Next"})
public class Next extends HttpServlet {

    private HTMLView view;

    /**
     * constructor
     */
    public Next() {
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

        HttpSession session = request.getSession();
        TuringMachine tm = (TuringMachine) session.getAttribute("turingmachine");
        try {
            Transition transition = tm.next();

            Integer noSteps = (Integer) session.getAttribute("noMove");
            String cookieMessage = "(" + transition.getInitialState() + ",[";
            for (String symbol : transition.getInitialSymbols()) {
                cookieMessage += symbol + ", ";
            }
            cookieMessage = cookieMessage.substring(0, cookieMessage.length() - 2);
            cookieMessage += "]) --> ( " + transition.getResultState() + ", [";
            for (String symbol : transition.getResultSymbols()) {
                cookieMessage += symbol + ", ";
            }
            cookieMessage = cookieMessage.substring(0, cookieMessage.length() - 2);
            cookieMessage += "], [";
            for (Move m : transition.getResultMoves()) {
                cookieMessage += m.toString() + ", ";
            }
            cookieMessage = cookieMessage.substring(0, cookieMessage.length() - 2);
            cookieMessage += "])";

            Cookie cookie = new Cookie(noSteps.toString(), cookieMessage);
            session.setAttribute("noMove", (noSteps + 1));

            cookie.setMaxAge(-1);
            response.addCookie(cookie);

            view.showMachine(request, response);
        } catch (TMException ex) {
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
