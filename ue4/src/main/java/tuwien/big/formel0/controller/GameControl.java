package tuwien.big.formel0.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import formel0api.Game;
import formel0api.Player;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import tuwien.big.formel0.twitter.*;
import at.ac.tuwien.big.we.highscore.Failure;
import at.ac.tuwien.big.we.highscore.PublishHighScoreService;
import at.ac.tuwien.big.we.highscore.PublishHighScoreEndpoint;
import at.ac.tuwien.big.we.highscore.data.HighScoreRequestType;
import at.ac.tuwien.dbai.education.ssd.ss13.uebung.tournament.*;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import tuwien.big.formel0.utilities.Utility;

@ManagedBean(name = "gc")
@SessionScoped
public class GameControl {

    Player player;
    Player computer;
    Game game;
    int playerscore = 0;
    int computerscore = 0;
    int round = 1;
    String playername;

    String uuid;
    boolean twitterMessageVisible = false;

    public GameControl() {
        this("Susi");
    }

    /**
     * Initializes a new game.
     */
    public GameControl(String playername) {
        this.playername = playername;
        init();
    }

    public void init() {
        player = new Player(playername);
        computer = new Player("Deep Blue");
        this.game = new Game(player, computer);
        round = 1;
        uuid = null;
        twitterMessageVisible = false;
    }

    /**
     * Returns the time already spent on this game
     *
     * @return the time already spent on this game
     */
    public String getTime() {
        long milliseconds = game.getSpentTime();
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                (TimeUnit.MILLISECONDS.toSeconds(milliseconds)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds))));
    }

    /**
     * Specifies whether this game is over or not
     *
     * @return <code>true</code> if this game is over, <code>false</code>
     * otherwise.
     */
    public boolean isGameOver() {
        return game.isGameOver();
    }

    /**
     * Returns the rounds already played in this game
     *
     * @return the rounds already played in this game
     */
    public int getRound() {
        return round;
    }

    /**
     * Returns the currently leading player
     *
     * @return the currently leading player
     */
    public Player getLeader() {
        return game.getLeader();
    }

    public String getUUID() {
        return uuid;
    }

    public boolean isTwitterMessageVisible() {
        return twitterMessageVisible;
    }

    /**
     * Rolls the dice for the player
     */
    public void doRound() {
        if (isGameOver()) {
            return;
        }

        this.playerscore = game.rollthedice(player);
        if (!isGameOver()) {
            this.computerscore = game.rollthedice(computer);
        } else {
            this.computerscore = 0;
        }
        ++round;

        if (isGameOver()) {
            // this part is (and must be) run only once per game (when the game is won)

            PublishHighScoreService highScoreService = new PublishHighScoreService();
            PublishHighScoreEndpoint highScore = highScoreService.getPublishHighScorePort();
            
            at.ac.tuwien.big.we.highscore.data.ObjectFactory factory = new at.ac.tuwien.big.we.highscore.data.ObjectFactory();
            HighScoreRequestType highScoreRequest = factory.createHighScoreRequestType();
            highScoreRequest.setUserKey("34EphAp2C4ebaswu");
            
            ObjectFactory objectFactory = new ObjectFactory();
            TournamentType tournament = objectFactory.createTournamentType();
            
            GregorianCalendar currentTime = new GregorianCalendar();
            try {
                XMLGregorianCalendar XMLCurrentTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(currentTime);
                tournament.setStartDate(XMLCurrentTime);
                tournament.setEndDate(XMLCurrentTime);
                tournament.setRegistrationDeadline(XMLCurrentTime);
            } catch(Exception e) {
                System.err.println(e.getMessage());
            }
            
            TournamentType.Players players = objectFactory.createTournamentTypePlayers();
            TournamentType.Players.Player currentPlayer = objectFactory.createTournamentTypePlayersPlayer();
            
            EntityManager entityManager;
            tuwien.big.formel0.entities.Player tablePlayer;

            entityManager = Utility.getEntityManagerFactory().createEntityManager();

            entityManager.getTransaction().begin();
            
            tablePlayer = (tuwien.big.formel0.entities.Player)entityManager.find(tuwien.big.formel0.entities.Player.class, game.getPlayer().getName());

            String[] dateOfBirth = new String[3];
            dateOfBirth[0] = "1";
            dateOfBirth[1] = "0";
            dateOfBirth[2] = "1990";
            String gender = "MALE";
            
            if(tablePlayer != null) {
                if(tablePlayer.getSex().equals("w")) {
                    gender = "FEMALE";
                }
                
                String birthday = tablePlayer.getBirthday();
                String delim = "\\.";
                dateOfBirth = birthday.split(delim);
                
                entityManager.getTransaction().commit();
                entityManager.close();
            } else {
                entityManager.getTransaction().rollback();
                entityManager.close();
            }
            
            GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(dateOfBirth[2]), Integer.parseInt(dateOfBirth[1]) - 1, Integer.parseInt(dateOfBirth[0]));
            try {
                XMLGregorianCalendar birthdate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
                currentPlayer.setDateOfBirth(birthdate);
            } catch(Exception e) {
                System.err.println(e.getMessage());
            }
            
            currentPlayer.setUsername(player.getName());
            currentPlayer.setGender(gender);
            
            players.getPlayer().add(currentPlayer);
            tournament.setPlayers(players);
            
            TournamentType.Rounds rounds = objectFactory.createTournamentTypeRounds();
            TournamentType.Rounds.Round tournamentRound = objectFactory.createTournamentTypeRoundsRound();
            tournamentRound.setNumber(0);
            
            GameType currentGame = new GameType();
            try {
                XMLGregorianCalendar XMLCurrentTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(currentTime);
                currentGame.setDate(XMLCurrentTime);
            } catch(Exception e) {
                System.err.println(e.getMessage());
            }   
            currentGame.setStatus("finished");
            long milliseconds = game.getSpentTime();
            currentGame.setDuration(BigInteger.valueOf(TimeUnit.MILLISECONDS.toSeconds(milliseconds)));
            // if computer is leader, send "Computer"
            if(game.getLeader().getName().equals(game.getComputer().getName())) {
                currentGame.setWinner("Computer");
            } else {
                currentGame.setWinner(game.getLeader().getName());
            }
            
            GameType.Players gamePlayers = objectFactory.createGameTypePlayers();
            GameType.Players.Player gamePlayer = objectFactory.createGameTypePlayersPlayer();
            gamePlayer.setRef(game.getPlayer().getName());
            gamePlayers.getPlayer().add(gamePlayer);
            currentGame.setPlayers(gamePlayers);
            
            tournamentRound.getGame().add(currentGame);
            rounds.getRound().add(tournamentRound);
            tournament.setRounds(rounds);
            highScoreRequest.setTournament(tournament);
            try {
                uuid = highScore.publishHighScore(highScoreRequest);
                Logger logger = Logger.getLogger("uuid-log");
                logger.info(uuid);
            } catch(Failure failure) {
                System.err.println(failure.getFaultInfo().getDetail());
            }
            
            Date date = new Date();

            TwitterStatusMessage msg = new TwitterStatusMessage(player.getName(), uuid, date);
            try {
                (new TwitterClient()).publishUuid(msg);
                twitterMessageVisible = true;
            }
            catch(Exception e) {
                // just drop it silently
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Returns the score thrown by the player
     *
     * @return the score thrown by the player
     */
    public String getDiceResource() {
        return "img:wuerfel" + getPlayerScore() + ".png";
    }

    /**
     * Returns the score thrown by the player
     *
     * @return the score thrown by the player
     */
    public int getPlayerScore() {
        return this.playerscore;
    }

    /**
     * Returns the score of the computer
     *
     * @return the score of the computer-controlled opponent
     */
    public int getComputerScore() {
        return this.computerscore;
    }

    /**
     * Returns player 1 of the game
     *
     * @return player 1 of the game
     */
    public Player getPlayer1() {
        return this.player;
    }

    /**
     * Return player 2 of the game
     *
     * @return player 2 of the game
     */
    public Player getPlayer2() {
        return this.computer;
    }
}