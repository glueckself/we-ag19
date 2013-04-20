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
    
    private GameList formel0Games;

    public Formel0()
            throws ServletException {
        super.init();
        formel0Games = new GameList();
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        String redirectTarget;
        
        Formel0Bean currentData;
        String command = request.getParameter("command");
        if(command == null)
            command="";
        
        if(session.isNew()) {
            currentData=formel0Games.createGame(session.getId());
            redirectTarget = "index.jsp";
        } else {
            if(command.equals("newGame") || command.equals("")) {
                formel0Games.endGame(session.getId());
                currentData = formel0Games.createGame(session.getId());
                redirectTarget = "index.jsp";
            } else if (command.equals("dice")) {
                currentData = formel0Games.getGame(session.getId());
                currentData.nextRound();
                for(int i=0; i< Formel0Game.NUM_PLAYERS; i++) {
                    Formel0Game.throwDice(currentData, i);
                }
                redirectTarget = "index.jsp";
            } else if (command.equals("logout")) {
                formel0Games.endGame(session.getId());
                session.invalidate();
                redirectTarget = "logout.html";
                currentData = null;
            } else {
                currentData = null;
                response.sendError(1,"Invalid command");
                return;
            }
        }
        
        RequestDispatcher view = request.getRequestDispatcher(redirectTarget);
        request.setAttribute("gameData",currentData);
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
