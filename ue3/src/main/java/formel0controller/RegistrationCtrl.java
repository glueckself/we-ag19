package formel0controller;

import java.util.GregorianCalendar;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import userDB.Sex;
import userDB.User;
import userDB.UserDB;

@ManagedBean
@RequestScoped
public class RegistrationCtrl {

    @ManagedProperty(value = "#{userDB}")
    UserDB userDB;
    @ManagedProperty(value = "")
    String firstName;
    @ManagedProperty(value = "")
    String lastName;
    @ManagedProperty(value = "")
    GregorianCalendar dateOfBirth;
    @ManagedProperty(value = "")
    Sex sex;
    @ManagedProperty(value = "")
    String username;
    @ManagedProperty(value = "")
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

    public GregorianCalendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(GregorianCalendar dateOfBirth) {
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

    public String register() {
        User user = new User(getFirstName(), getLastName(), getSex(), getUsername(), getPassword(), getDateOfBirth());
        getUserDB().registerUser(user);
        return "/register_success.xhtml";
    }

    public void validateUsername(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        String str = (String) value;
        if (!Pattern.compile("^[a-zA-Z]+$").matcher(str).matches()) {
            throw new ValidatorException(new FacesMessage(JSFHelper.getLocalized("usernameInvalid")));
        }
        if (getUserDB().hasUser(str)) {
            throw new ValidatorException(new FacesMessage(JSFHelper.getLocalized("usernameExists")));
        }
    }

    public void validateDateOfBirth(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        GregorianCalendar date = (GregorianCalendar) value;
        if (date.after(new GregorianCalendar())) {
            throw new ValidatorException(new FacesMessage(JSFHelper.getLocalized("birthdayInFuture")));
        }
    }
}