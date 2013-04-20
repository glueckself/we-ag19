/*
 * Model class
 */

/**
 *
 * @author srdj
 */

import java.util.Random;
import java.util.Date;

public class Formel0Game {
    public static final int NUM_FIELDS = 7;
    public static final int NUM_PLAYERS = 2;
    public static final int[] oilSpits =  {2, 5};
    
    private int playerPos[];
    private int lastDiceNum[];
    
    private boolean gameFinished = false;
    private Date startTime;
    
    public Formel0Game() {
        lastDiceNum = new int[NUM_PLAYERS];
        playerPos = new int[NUM_PLAYERS];
        
        for(int i=0; i< NUM_PLAYERS; i++) {
            playerPos[i]=0;
            lastDiceNum[i]=-1;
        }
        
        startTime = new Date();
    }
    
    public void throwDice(int currentPlayer)
        throws IllegalArgumentException {
        if(currentPlayer < 0 && currentPlayer >= NUM_PLAYERS)
            throw  new IllegalArgumentException("currentPlayer must be 0 <= currentPlayer < NUM_PLAYERS");
        
        Random rnd = new Random();
        lastDiceNum[currentPlayer]=rnd.nextInt(4)+1;
        
        int nextPos = playerPos[currentPlayer]+lastDiceNum[currentPlayer];
        
        for(int i = 0; i < oilSpits.length; i++) {
            if(nextPos == oilSpits[i]) {
                playerPos[currentPlayer]=0;
                return;
            }
        }
        
        if(nextPos >= NUM_FIELDS) {
            playerPos[currentPlayer]=NUM_FIELDS-1;
            gameFinished=true;
        }
        
        playerPos[currentPlayer]=nextPos;
    }
    
    public int getPos(int player)
        throws IllegalArgumentException {
        if(player < 0 && player >= NUM_PLAYERS)
            throw  new IllegalArgumentException("currentPlayer must be 0 <= currentPlayer < NUM_PLAYERS");
        
        return playerPos[player];
    }
    
    public int getLastDice(int player)
        throws IllegalArgumentException {
        if(player < 0 && player >= NUM_PLAYERS)
            throw  new IllegalArgumentException("currentPlayer must be 0 <= currentPlayer < NUM_PLAYERS");
        
        return lastDiceNum[player];
    }
    
    public long getGameDuration() {
        return (new Date().getTime() - startTime.getTime());
    }
    
    public boolean isGameFinnished() {
        return gameFinished;
    }
}
