/*
 * Servlet (Controller)
 */

import Formel0.Formel0Game;
import Formel0.Formel0Bean;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 *
 * @author srdj
 */
public class Formel0 extends HttpServlet {
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String redirectTarget;

        String command = request.getParameter("command");
        if(command == null)
            command="";

        Formel0Bean gameData = (Formel0Bean)session.getAttribute("gameData");

        if(session.isNew() || command.equals("newGame") || command.equals("") || gameData == null) {
            gameData = Formel0Game.createGame();
            session.setAttribute("gameData", gameData);
            redirectTarget = "/index.jsp";
        }
        else if (command.equals("dice")) {
            if (!gameData.isGameFinished()) {
                Formel0Game.playRound(gameData);
            }
            redirectTarget = "/index.jsp";
        }
        else if (command.equals("logout")) {
            session.invalidate();
            redirectTarget = "/logout.html";
        }
        else {
            response.sendError(1,"Invalid command");
            return;
        }

        RequestDispatcher view = getServletContext().getRequestDispatcher(redirectTarget);
        view.forward(request, response);  
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request,response);
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request,response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controller for Formel0 game";
    }// </editor-fold>
}
