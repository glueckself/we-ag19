package userDB;

import java.util.HashMap;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class UserDB {
    private HashMap<String,User> users;
    
    public UserDB() {
        users = new HashMap<String,User>();
    }
    
    public boolean login(String username, String password) {
        User user=users.get(username);
        
        if(user == null)
            return false;
        
        return user.getPassword().equals(password);
    }
    
    public User getUser(String username) {
        return users.get(username);
    }
    
    public void registerUser(User user) {
        if(user == null)
            return; //maybe throw something, just for fun ;)
        
        users.put(user.getUserName(),user);
    }
}
