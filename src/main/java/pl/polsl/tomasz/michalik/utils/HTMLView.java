package pl.polsl.tomasz.michalik.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.http.*;
import pl.polsl.tomasz.michalik.model.Tape;
import pl.polsl.tomasz.michalik.model.TuringMachine;

/**
 * class generating the html presented as a response of both servlets
 *
 * @author Tomasz Michalik
 * @version 1.0
 */
public class HTMLView {

    private int columnNumber;

    /**
     * constructor
     *
     * @param columnNumber number of columns in turing machine representation
     */
    public HTMLView(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     *
     * @param message
     * @param response
     * @throws IOException
     */
    public void handleException(String message, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel=\"stylesheet\" href=\"turingMachine.css\">");
        out.println("<title>Servlet Next</title>");
        out.println("</head>");
        out.println("<body>");
        
        out.println("<div>");
        
        out.println("<h1> program encoutered the following problem: </h1>");
        out.println("<p>" + message.replace("<", "&lt").replace(">", "&gt") + "</p>");
        out.println("</div> </body>");
        out.println("</html>");
        out.close();
    }

    /**
     * shows the turing machine by handling the request
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void showMachine(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE HTML>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>many-tape turing machine</title>");
        out.println("<link rel=\"stylesheet\" href=\"turingMachine.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<div>");
        HttpSession session = request.getSession();
        TuringMachine tm = (TuringMachine) session.getAttribute("turingmachine");

        out.println("<h1> multi-tape turing machine </h1>");
        
        

        int halfColumns = columnNumber / 2;
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        for (Tape t : tm.getTapes()) {
            ArrayList<String> row = new ArrayList<String>(columnNumber);
            for (int i = 0; i < columnNumber; i++) {
                row.add(new String());
            }
            ArrayList<String> contents = t.getContents();

            //setting the columns to the left of current position
            {
                int cPos = t.getPosition();
                int rPos = halfColumns;
                while (cPos >= 0 && rPos >= 0) {
                    row.set(rPos, contents.get(cPos));
                    rPos--;
                    cPos--;
                }
                while (rPos >= 0) {
                    row.set(rPos, tm.getBlank());
                    rPos--;
                }
            }

            //setting the columns to the right of current position
            {
                int cPos = t.getPosition();
                int rPos = halfColumns;
                while (cPos < contents.size() && rPos < columnNumber) {
                    row.set(rPos, contents.get(cPos));
                    rPos++;
                    cPos++;
                }
                while (rPos < columnNumber) {
                    row.set(rPos, tm.getBlank());
                    rPos++;
                }
            }
            data.add(row);
        }

        out.println("<table>");
        
        out.println("<tr> current state : " + tm.getCurrentState() + "</tr>");
        
        for (ArrayList<String> row : data) {
            out.println("<tr>");
            for (int i = 0; i< row.size(); i++) {
                if (i == columnNumber/2){
                    out.println("<td class=\"highlight\"> " + row.get(i) + "</td>");
                }
                else {
                    out.println("<td> " + row.get(i) + "</td>");
                }
            }
            out.println("</tr>");
        }
        out.println("</table>");

        out.println("<form action=Next> <input type=submit value=next> </form>");

        //case sensitive!!!
        out.println("<form action=SaveConfiguration> <input type=submit value=save configuration> </form>");
        
        Integer noSteps = 0 ;
        for (Cookie c: request.getCookies()){
            if (c.getName().equals("noSteps") && !c.getValue().isEmpty()){
                noSteps = Integer.valueOf(c.getValue());
            }
        }
        
        out.println("<p> number of steps (read from cookies): " + noSteps.toString() + "</p>");

        out.println("</div>");
        
        out.println("</body > </html>");

        out.close();

    }
}
