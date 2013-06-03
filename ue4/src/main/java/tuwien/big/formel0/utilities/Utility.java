package tuwien.big.formel0.utilities;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Dieter
 */
public class Utility {
    private static EntityManagerFactory emf;

    public static String getResourceText(FacesContext ctx,
            String bundleName, String key) {
        String text;
        try {
            Application app = ctx.getApplication();
            ResourceBundle bundle = app.getResourceBundle(
                    ctx, bundleName);
            text = bundle.getString(key);
        } catch (MissingResourceException e) {
            return "???" + key + "???";
        }

        return text;
    }
    

    
    public static EntityManagerFactory getEntityManagerFactory() {
        if(emf == null)
            emf=Persistence.createEntityManagerFactory("lab4");
        
        return emf;
    }
}
