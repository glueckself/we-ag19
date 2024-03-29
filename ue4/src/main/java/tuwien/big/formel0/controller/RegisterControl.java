package tuwien.big.formel0.controller;

import tuwien.big.formel0.utilities.Utility;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import tuwien.big.formel0.entities.Player;
import tuwien.big.formel0.picasa.RaceDriver;

/**
 *
 */
@ManagedBean(name = "rc")
@RequestScoped
public class RegisterControl {    
    @ManagedProperty(value = "#{player}")
    private Player newplayer;
    @ManagedProperty(value = "false")
    private boolean displayterms;
    @ManagedProperty(value = "#{false}")
    private boolean registrationsuccessful;

    public List<RaceDriver> getRaceDrivers() {
        EntityManager em = Utility.getEntityManagerFactory().createEntityManager();
        Query query = em.createQuery("Select emp from RaceDriver emp");
        List<RaceDriver> rd = query.getResultList();
        return rd;
    }
    
        // TODO: Load from database instead of a static variable!
        // Initialization of database with picasa data should happen somewhere else

    /**
     * Creates a new instance of RegisterControl
     */
    public RegisterControl() {
        registrationsuccessful=false;
        return;
    }

    public String register() {
        EntityManager em;
        Player player;
    
        em=Utility.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        
        player = (Player)em.find(Player.class,newplayer.getName());
        if(player != null) {
            em.getTransaction().rollback();
            em.close();
            return "register";
        }
        
        player = new Player(newplayer);
        
        em.persist(player);
        em.flush();
        em.getTransaction().commit();
        registrationsuccessful = true;
        em.close();
        return "register";
    }

    //Checks if the display checkbox changed
    public void displayChanged(ValueChangeEvent e) {
        Boolean show = (Boolean) e.getNewValue();
        if (show != null) {
            displayterms = show;
        }

        FacesContext.getCurrentInstance().renderResponse();
    }

    //Validation of the birthday
    public void validateBirthday(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        String bd = (String) value;

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        format.setLenient(false);

        try {
            format.parse(bd);
        } catch (ParseException e) {
            String i18ntext = Utility.getResourceText(ctx, "msg", "birthdateformat");

            FacesMessage msg = new FacesMessage(
                    FacesMessage.SEVERITY_WARN, i18ntext, null);

            throw new ValidatorException(msg);
        }
    }

    /**
     * @return the newplayer
     */
    public Player getNewplayer() {
        return newplayer;
    }

    /**
     * @param newplayer the newplayer to set
     */
    public void setNewplayer(Player newplayer) {
        this.newplayer = newplayer;
    }

    /**
     * @return the displayterms
     */
    public boolean isDisplayterms() {
        return displayterms;
    }

    /**
     * @param displayterms the displayterms to set
     */
    public void setDisplayterms(boolean displayterms) {
        this.displayterms = displayterms;
    }

    /**
     * @return the registrationsuccessful
     */
    public boolean isRegistrationsuccessful() {
        return registrationsuccessful;
    }

    /**
     * @param registrationsuccessful the registrationsuccessful to set
     */
    public void setRegistrationsuccessful(boolean registrationsuccessful) {
        this.registrationsuccessful = registrationsuccessful;
    }
}
