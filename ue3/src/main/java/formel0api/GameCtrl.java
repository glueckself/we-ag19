package formel0api;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class GameCtrl {
    @ManagedProperty(value = "#{game}")
    Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public GameCtrl() {
    	Player player = new Player("Player");
    	Player computer = new Player("Computer");
        this.game = new Game(player, computer);
    }

    public String getName() {
    	return this.game.getPlayer.getName();
    }

    public String test() {
    	return "/login.xhtml";
    }
}
