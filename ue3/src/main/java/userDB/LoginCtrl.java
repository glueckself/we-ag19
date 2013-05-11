package userDB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

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
    boolean isFirstLoginAttempt = true;

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

    public boolean isLoginFailed() {
        return !isFirstLoginAttempt && (user == null);
    }

    public String login()
    {
        user = userDB.login(username, password);
        isFirstLoginAttempt = false;
        password = null;

        if (user == null) {
            return "/index.xhtml";
        } else {
            return "/table.xhtml";
        }
    }
}