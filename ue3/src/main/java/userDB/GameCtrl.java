package userDB;

import formel0api.Game;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class GameCtrl {
    @ManagedProperty(value = "#{loginCtrl.user}")
    User user;

//    @ManagedProperty(value = "#{loginCtrl.user.currentGame}")
//    Game game;
//
//    public Game getGame() {
//        return game;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Game getGame() {
        return user.getCurrentGame();
    }

    public String getSpentTimeFormatted() {
        long total_millis = getGame().getSpentTime();
        long total_seconds = total_millis / 1000;
        long part_seconds = total_seconds % 60;
        long total_minutes = total_seconds / 60;
        
        return String.format("%02d:%02d", total_minutes, total_seconds);
    }

}
