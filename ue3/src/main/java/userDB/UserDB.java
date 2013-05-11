package userDB;

import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

@ManagedBean
@ApplicationScoped
public class UserDB {
    private HashMap<String,User> users;
    
    public UserDB() {
        users = new HashMap<String,User>();
    }

    // Returns the User object if login is successful, null otherwise
    public User login(String username, String password) {
        User user=users.get(username);

        if (user != null && !user.getPassword().equals(password))
            user = null;

        return user;
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
