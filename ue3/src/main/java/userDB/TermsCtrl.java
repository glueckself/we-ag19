package userDB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class TermsCtrl {

    @ManagedProperty(value = "false")
    boolean termsVisible;

    public boolean isTermsVisible() {
        return termsVisible;
    }

    public void setTermsVisible(boolean termsVisible) {
        this.termsVisible = termsVisible;
    }
}
