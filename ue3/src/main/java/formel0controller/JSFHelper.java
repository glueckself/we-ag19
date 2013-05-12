package formel0controller;

import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

public class JSFHelper
{
    public static String getLocalized(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle text = ResourceBundle.getBundle("i18n", context.getViewRoot().getLocale());
        return text.getString(key);
    }
}
