package formel0controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import userDB.User;
import userDB.UserDB;

@ManagedBean
@SessionScoped
public class LoginCtrl
{
    @ManagedProperty(value="#{userDB}")
    UserDB userDB;

    // These should be request parameters...
    @ManagedProperty(value="")
    String username;
    @ManagedProperty(value="")
    String password;

    User user = null;

    public UserDB getUserDB() {
        return userDB;
    }

    public void setUserDB(UserDB userDB) {
        this.userDB = userDB;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public boolean isLoginValid() {
        return (user != null);
    }

    public String login()
    {
        user = userDB.login(username, password);
        password = null;

        if (user == null) {
            return "/login_failed.xhtml";
        } else {
            return "/table.xhtml";
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml";
    }
}