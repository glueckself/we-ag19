package tuwien.big.formel0.entities;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import tuwien.big.formel0.picasa.RaceDriver;

@Entity
@Table(name = "Player")
@ManagedBean(name = "player")
@NoneScoped
public class Player
implements Serializable {

    private String firstname = null;
    private String lastname = null;
    @Id
    private String name = null;
    private String password = null;
    private String birthday = null;
    private String sex = null;
  
    @ManyToOne
    private RaceDriver avatar;
    

    /**
     * Creates a new instance of Player
     */
    public Player() {
    }
    
    public Player(Player original) {
        firstname=original.firstname;
        lastname=original.lastname;
        name=original.name;
        password=original.password;
        birthday=original.birthday;
        sex=original.sex;
        avatar=original.avatar;
    }

    /**
     * @return the name
     */
        public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    
    /**
     * @return the driver
     */
    public RaceDriver getAvatar() {
        return avatar;
    }

    /**
     * @param driver the driver to set
     */
    public void setAvatar(RaceDriver driver) {
        this.avatar = driver;
    }
    
}
