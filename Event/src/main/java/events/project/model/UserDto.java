package events.project.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;

public class UserDto {


    @NotEmpty(message = "First name may not be empty")
    private String firstName;
    @NotEmpty(message = "Last name may not be empty")
    private String lastName;
    @NotEmpty(message = "Email may not be empty")
    @Email
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


//        public List<Event> getEvents() {
//            return events;
//        }
//
//        public void setEvents(List<Event> events) {
//            this.events = events;
//        }


    @Override
    public String toString() {
        return "User ["
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", email=" + email
                + ", password=" + password
                + "]";
    }

    public static UserDto.UserBuilder New() {
        return new UserDto.UserBuilder();
    }

    public static class UserBuilder {

        private UserDto user;

        private UserBuilder() {
            user = new UserDto();
        }

        public UserDto.UserBuilder firstName(String firstName) {
            user.firstName = firstName;
            return this;
        }

        public UserDto.UserBuilder lastName(String lastName) {
            user.lastName = lastName;
            return this;
        }

        public UserDto.UserBuilder email(String email) {
            user.email = email;
            return this;
        }

        public UserDto build() {
            return user;
        }
    }
}

