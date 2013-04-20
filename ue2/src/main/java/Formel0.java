/*
 * Servlet (Controller)
 */

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 *
 * @author srdj
 */
public class Formel0 extends HttpServlet {
    
    private GameList formel0Games;

    @Override
    public void init()
            throws ServletException {
        super.init();
        formel0Games = new GameList();
    }
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        String redirectTarget;
        
        Formel0Game currentGame;
        String command = request.getParameter("command");
        
        if(session.isNew()) {
            formel0Games.createGame(session.getId());
        }
        
        if(command == "newGame") {
            formel0Games.endGame(session.getId());
            currentGame = formel0Games.createGame(session.getId());
            redirectTarget = "index.jsp";
        } else if (command == "dice") {
            currentGame = formel0Games.getGame(session.getId());
            for(int i=0; i< Formel0Game.NUM_PLAYERS; i++) {
                currentGame.throwDice(i);
            }
            redirectTarget = "index.jsp";
        } else if (command == "logout") {
            formel0Games.endGame(session.getId());
            redirectTarget = "logout.html";
            currentGame = null;
        } else {
            currentGame = null;
            redirectTarget = "error.html";
        }
        
        RequestDispatcher view = request.getRequestDispatcher(redirectTarget);
        request.setAttribute("game",currentGame);
        view.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
    }

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
        processRequest(request, response);
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
