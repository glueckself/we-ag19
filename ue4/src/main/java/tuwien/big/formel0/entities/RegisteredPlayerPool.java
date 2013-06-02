package tuwien.big.formel0.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import tuwien.big.formel0.picasa.RaceDriver;

/**
 *
 * Contains all current registered players
 */
@ManagedBean(name = "rpp")
@ApplicationScoped
public class RegisteredPlayerPool {

    ConcurrentMap<String, Player> regplayers = null;

    /**
     * Creates a new instance of RegisteredPlayerPool
     */
    public RegisteredPlayerPool() {
        regplayers = new ConcurrentHashMap<String, Player>();

        //Add test player
        Player tp = new Player();
        tp.setName("t");
        tp.setPassword("t");
        RaceDriver d = new RaceDriver();
        d.setName("Mika Haekkinen");
        d.setUrl("https://lh6.googleusercontent.com/-DeoFoqUTE2M/UXJX1Xca-cI/AAAAAAAAAFM/tZLUlhmxvIM/s144/mika_hakkinen.jpg");
        d.setWikiUrl("https://de.wikipedia.org/wiki/Mika_Häkkinen");
        tp.setAvatar(d);
        regplayers.put("t", tp);
    }

    public boolean addPlayer(Player p) {
        return regplayers.putIfAbsent(p.getName(), p) == null;
    }

    public Player getRegisteredPlayer(String username, String password) {
        Player curplayer;

        if ((curplayer = regplayers.get(username)) != null) {
            if (curplayer.getPassword().equals(password)) {
                return curplayer;
            }
        }

        return null;
    }

    /**
     * @return the players
     */
    public List<Player> getRegplayers() {
        return new ArrayList<Player>(regplayers.values());
    }
}
