package events.project.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty (message = "First name may not be empty")
    private String firstName;
    @NotEmpty(message = "Last name may not be empty")
    private String lastName;
    @NotEmpty(message = "Email may not be empty")
    @Email
    private String email;
    @NotEmpty (message = "Password may not be empty")
    private String password;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();

    @Column
    @OneToMany(mappedBy = "user")
    private List<Event> events;

    public UserDto() {}

    public UserDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Set<UserRole> getRoles() {
        return roles;
    }

//        public List<Event> getEvents() {
//            return events;
//        }
//
//        public void setEvents(List<Event> events) {
//            this.events = events;
//        }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
    @Override
    public String toString() {
        return "User [id=" + id
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", email=" + email
                + ", password=" + password
                + ", roles=" + roles + "]";
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
        public UserDto bulid() {
            return user;
        }
    }
}

