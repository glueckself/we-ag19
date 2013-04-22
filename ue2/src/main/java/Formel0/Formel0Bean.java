package Formel0;


import java.util.Date;

/*
 * Model Class
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author srdj
 */
public class Formel0Bean {
    private int playerPos[];
    private int playerPrevPos[];
    private int lastDiceNum[];
    
    private boolean gameFinished = false;
    private Date startTime;
    
    private String playerNames[];
    
    private int round;
    
    public Formel0Bean(int numPlayers) {
        lastDiceNum = new int[numPlayers];
        playerPrevPos = new int[numPlayers];
        playerPos = new int[numPlayers];
        playerNames = new String[numPlayers];
        
        playerNames[0]="Super Mario";
        playerNames[1]="Super C";
        
        round=0;
        
        for(int i=0; i< numPlayers; i++) {
            playerPos[i]=0;
            playerPrevPos[i]=-1;
            lastDiceNum[i]=-1;
        }
        
        startTime = new Date();
    }
    
    public int getRound() {
        return round;
    }
    
    public void nextRound() {
        round++;
    }
    
    public String getPlayerName(int index) {
        return playerNames[index];
    }
    
    /**
     * @return the playerPos
     */
    public int getPlayerPos(int index) {
        return playerPos[index];
    }

    /**
     * @param playerPos the playerPos to set
     */
    public void setPlayerPos(int playerPos, int index) {
        this.playerPrevPos[index] = this.playerPos[index];
        this.playerPos[index] = playerPos;
    }
    
    public int getPlayerPrevPos(int index) {
        return playerPrevPos[index];
    }

    /**
     * @return the lastDiceNum
     */
    public int getLastDiceNum(int index) {
        return lastDiceNum[index];
    }

    /**
     * @param lastDiceNum the lastDiceNum to set
     */
    public void setLastDiceNum(int lastDiceNum, int index) {
        this.lastDiceNum[index] = lastDiceNum;
    }

    /**
     * @return the gameFinished
     */
    public boolean isGameFinished() {
        return gameFinished;
    }

    /**
     * @param gameFinished the gameFinished to set
     */
    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }
       
    public long getGameDuration() {
        return (new Date().getTime() - startTime.getTime());
    }
}
