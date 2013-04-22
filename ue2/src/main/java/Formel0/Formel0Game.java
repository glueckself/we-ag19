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
    private static final Random rnd = new Random();
    public static final int NUM_FIELDS = 7;
    public static final int NUM_PLAYERS = 2;
    public static final int[] oilSpits =  {2, 5};

    public static Formel0Bean createGame() {
        Formel0Bean newGame = new Formel0Bean();
        return newGame;
    }

    public static void playRound(Formel0Bean gameData) {
        gameData.nextRound();
        for (Formel0Bean.Player p : gameData.getPlayers()) {
            throwDice(gameData, p);
        }
    }

    public static void throwDice(Formel0Bean gameData, Formel0Bean.Player p) {
        int diceNum=rnd.nextInt(3)+1;
        p.setLastDiceNum(diceNum);
        
        int nextPos = p.getPos() + diceNum;
        
        for(int i = 0; i < oilSpits.length; i++) {
            if(nextPos == oilSpits[i]) {
                p.setPos(0);
                return;
            }
        }
        
        if(nextPos >= (NUM_FIELDS - 1)) {
            p.setPos(NUM_FIELDS-1);
            gameData.setGameFinished(true);
            return;
        }
        
        p.setPos(nextPos);
    }
}
