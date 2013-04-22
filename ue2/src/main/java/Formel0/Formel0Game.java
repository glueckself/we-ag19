package Formel0;

/*
 * Controller class
 */

/**
 *
 * @author srdj
 */

import Formel0.Formel0Bean;
import java.util.Random;

public class Formel0Game {
    public static final int NUM_FIELDS = 7;
    public static final int NUM_PLAYERS = 2;
    public static final int[] oilSpits =  {2, 5};

    public static Formel0Bean createGame() {
        Formel0Bean newGame = new Formel0Bean(Formel0Game.NUM_PLAYERS);
        return newGame;
    }

    public static void throwDice(Formel0Bean gameData, int currentPlayer)
        throws IllegalArgumentException {
        if(currentPlayer < 0 && currentPlayer >= NUM_PLAYERS)
            throw  new IllegalArgumentException("currentPlayer must be 0 <= currentPlayer < NUM_PLAYERS");
        
        Random rnd = new Random();
        int diceNum=rnd.nextInt(3)+1;
        gameData.setLastDiceNum(diceNum,currentPlayer);
        
        int nextPos = gameData.getPlayerPos(currentPlayer)+diceNum;
        
        for(int i = 0; i < oilSpits.length; i++) {
            if(nextPos == oilSpits[i]) {
                gameData.setPlayerPos(0,currentPlayer);
                return;
            }
        }
        
        if(nextPos >= NUM_FIELDS) {
            gameData.setPlayerPos(NUM_FIELDS-1,currentPlayer);
            gameData.setGameFinished(true);
            return;
        }
        
        gameData.setPlayerPos(nextPos,currentPlayer);
    }
}
