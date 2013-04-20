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
    private HashMap<String,Formel0Bean> games;
    
    public GameList() {
        games = new HashMap<String,Formel0Bean>();
    }
    
    public Formel0Bean getGame(String id) {
        return games.get(id);
    }
    
    public void endGame(String id) {
        games.remove(id);
    }
    
    public Formel0Bean createGame(String id) {
        Formel0Bean newGame = new Formel0Bean(Formel0Game.NUM_PLAYERS);
        games.put(id, newGame);
        return newGame;
    }
}
