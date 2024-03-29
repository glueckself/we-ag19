package formel0controller;

import formel0api.Game;
import formel0api.Player;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import userDB.User;

@ManagedBean
@SessionScoped
public class GameCtrl {
    @ManagedProperty(value = "#{loginCtrl}")
    LoginCtrl loginCtrl;
    
    private int lastResultPlayer;
    private int lastRoundPosPlayer;
    private int lastResultComputer;
    private int lastRoundPosComputer;
    
    public GameCtrl() {
        lastRoundPosPlayer = -1;
        lastRoundPosComputer = -1;
        lastResultPlayer=1;
        lastResultComputer=0;
    }

    public LoginCtrl getLoginCtrl() {
        return loginCtrl;
    }

    public void setLoginCtrl(LoginCtrl loginCtrl) {
        this.loginCtrl = loginCtrl;
    }

    public User getUser() {
        return loginCtrl.getUser();
    }

    public Game getGame() {
        return getUser().getCurrentGame();
    }

    public String getSpentTimeFormatted() {
        long total_millis = getGame().getSpentTime();
        long total_seconds = total_millis / 1000;
        long part_seconds = total_seconds % 60;
        long total_minutes = total_seconds / 60;
        
        return String.format("%02d:%02d", total_minutes, part_seconds);
    }

    public String rollDice() {
        lastRoundPosPlayer = getGame().getPlayer().getPosition();
        lastRoundPosComputer = getGame().getComputer().getPosition();
        getGame().nextRound();
        lastResultPlayer=getGame().rollthedice(getGame().getPlayer());
        lastResultComputer=getGame().rollthedice(getGame().getComputer());
        return "/table.xhtml";
    }

    public String startNewGame() {
        lastRoundPosPlayer = -1;
        lastRoundPosComputer = -1;
        getUser().startNewGame();
        lastResultComputer=0;
        lastResultPlayer=1;
        return "/table.xhtml";
    }

    public int getLastResultPlayer() {
        return lastResultPlayer;
    }

    public int getLastResultComputer() {
        return lastResultComputer;
    }

    public int getLastRoundPosPlayer() {
        return lastRoundPosPlayer;
    }

    public int getLastRoundPosComputer() {
        return lastRoundPosComputer;
    }

    public String getLeaderName() {
        Player leader = getGame().getLeader();
        if (leader != null) {
            return leader.getName();
        } else {
            return JSFHelper.getLocalized("noUniqueLeader");
        }
    }
}
