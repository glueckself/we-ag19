package userDB;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

@ManagedBean
@RequestScoped
public class RegistrationCtrl
{
    @ManagedProperty(value="#{userDB}")
    UserDB userDB;

    @ManagedProperty(value="")
    String firstName;
    @ManagedProperty(value="")
    String lastName;
    @ManagedProperty(value="")
    Date dateOfBirth;
    @ManagedProperty(value="")
    Sex sex;
    @ManagedProperty(value="")
    String username;
    @ManagedProperty(value="")
    String password;

    public UserDB getUserDB() {
        return userDB;
    }

    public void setUserDB(UserDB userDB) {
        this.userDB = userDB;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Sex getSexFemale() {
        return Sex.FEMALE;
    }

    public Sex getSexMale() {
        return Sex.MALE;
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

    public String register()
    {
        // TODO
        return "/register.xhtml";
    }
}