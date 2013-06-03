package tuwien.big.formel0.picasa;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import tuwien.big.formel0.entities.Player;

/**
 * Represents a race driver from Picasa
 * @author pl
 *
 */
@Entity
@Table(name = "Avatar")
public class RaceDriver
implements Serializable {

    @Id
    private String name;
    private String url;
    private String wikiUrl;
    
    @OneToMany
    private List<Player> player;

    public RaceDriver() {
        player=new LinkedList<Player>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    public boolean equals(Object object) {
        if(!(object instanceof RaceDriver))
            return false;
        
        RaceDriver rd = (RaceDriver)object;
        
        if(!name.equals(rd.name))
            return false;
        if(!url.equals(rd.url))
            return false;
        if(!wikiUrl.equals(rd.wikiUrl))
            return false;
        
        return true;        
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }   

    /**
     * @return the player
     */
    public List<Player> getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(List<Player> player) {
        this.player = player;
    }

}
