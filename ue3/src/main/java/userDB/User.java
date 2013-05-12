package userDB;

import formel0api.Game;
import formel0api.Player;
import java.beans.PropertyChangeSupport;
import java.util.GregorianCalendar;

public class User {
    public static final String PROP_FIRSTNAME = "PROP_FIRSTNAME";
    public static final String PROP_LASTNAME = "PROP_LASTNAME";
    public static final String PROP_BIRTHDAY = "PROP_BIRTHDAY";
    public static final String PROP_SEX = "PROP_SEX";
    public static final String PROP_USERNAME = "PROP_USERNAME";
    public static final String PROP_PASSWORD = "PROP_PASSWORD";
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    
    private String firstName;
    private String lastName;
    private GregorianCalendar birthday;
    private Sex sex;
    
    private String userName;
    private String password;

    private Game currentGame;
    
    public User(String _firstName, String _lastName, Sex _sex, String _userName, String _password, GregorianCalendar _birthday) {
        firstName=_firstName;
        lastName=_lastName;
        sex=_sex;
        userName=_userName;
        password=_password;
        birthday=_birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public GregorianCalendar getBirthday() {
        return birthday;
    }

    public Sex getSex() {
        return sex;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Game getCurrentGame() {
        if (currentGame == null) {
            startNewGame();
        }
        return currentGame;
    }

    public void startNewGame() {
    	Player player = new Player(firstName + " " + lastName);
    	Player computer = new Player("Super C");
        currentGame = new Game(player, computer);
    }
}
