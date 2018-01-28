package events.project.modelDto;

import lombok.Builder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Data Transfer Objects dla encji User
 * @version 1.1
 */
@Builder
public class UserDto {

    @NotEmpty(message = "First name may not be empty")
    private String firstName;
    @NotEmpty(message = "Last name may not be empty")
    private String lastName;
    @NotEmpty(message = "Email may not be empty")
    @Email(message = "It is not e-mail")
    private String email;
    @NotEmpty(message = "Password may not be empty")
    private String password;


    public UserDto() {
    }

    public UserDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

