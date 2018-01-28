package events.project.modelDto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * Obiekt zawiera dane logowania
 * @version 1.1
 */
public class UserLogin {
    @Email(message = "It is not e-mail")
    private String login;
    @NotEmpty(message = "Password may not be empty")
    private String password;

    public UserLogin() {
    }

    public UserLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String name) {
        this.login = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}