package formel0controller;

import formel0api.Game;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import userDB.User;

@ManagedBean
@SessionScoped
public class GameCtrl {
    @ManagedProperty(value = "#{loginCtrl}")
    LoginCtrl loginCtrl;

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
        getGame().rollthedice(getGame().getComputer());
        getGame().rollthedice(getGame().getPlayer());
        return "/table.xhtml";
    }

    public String startNewGame() {
        getUser().startNewGame();
        return "/table.xhtml";
    }
}
