package Formel0;


import java.util.*;

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

    public class Player {
        private String name;
        private int pos;
        private int prevPos;
        private int lastDiceNum;

        private Player(String name) {
            this.name = name;
            this.pos = 0;
            this.prevPos = -1;
            this.lastDiceNum = 0;
        }

        public String getName() {
            return name;
        }

        public int getPos() {
            return pos;
        }

        public int getPrevPos() {
            return prevPos;
        }

        public void setPos(int newPos) {
            prevPos = pos;
            pos = newPos;
        }

        public int getLastDiceNum() {
            return lastDiceNum;
        }

        public void setLastDiceNum(int newLastDiceNum) {
            lastDiceNum = newLastDiceNum;
        }
    }

    private final List<Player> players;
    private final Player computerPlayer;
    private final Player userPlayer;
    private boolean gameFinished = false;
    private Date startTime;
    
    private int round;

    public Formel0Bean() {
        userPlayer = new Player("Super Mario");
        computerPlayer = new Player("Super C");

        players = new ArrayList<Player>();
        players.add(userPlayer);
        players.add(computerPlayer);

        round = 0;
        
        startTime = new Date();
    }
    
    public int getRound() {
        return round;
    }
    
    public void nextRound() {
        round++;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public Player getUserPlayer() {
        return userPlayer;
    }

    public Player getComputerPlayer() {
        return computerPlayer;
    }

    public String getLeaderName() {
        Player bestPlayer = null;
        int currentMax = -1;

        for(Player p : players) {
            if (p.pos > currentMax) {
                bestPlayer = p;
                currentMax = p.pos;
            } else if (p.pos == currentMax) {
                bestPlayer = null;
            }
        }

        if (bestPlayer == null) {
            return "mehrere";
        } else {
            return bestPlayer.name;
        }
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
