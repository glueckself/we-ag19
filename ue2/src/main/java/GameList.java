/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author srdj
 */

import java.util.HashMap;

public class GameList {
    private HashMap<String,Formel0Game> games;
    
    public GameList() {
        games = new HashMap<String,Formel0Game>();
    }
    
    public Formel0Game getGame(String id) {
        return games.get(id);
    }
    
    public void endGame(String id) {
        games.remove(id);
    }
    
    public Formel0Game createGame(String id) {
        Formel0Game newGame = new Formel0Game();
        games.put(id, newGame);
        return newGame;
    }
}
