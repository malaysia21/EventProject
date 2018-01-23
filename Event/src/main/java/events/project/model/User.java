package events.project.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


    @Entity
    public class User {

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

        public User() {}

        public User(String firstName, String lastName, String email, String password) {
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

        public static User.UserBuilder New() {
            return new User.UserBuilder();
        }

        public static class UserBuilder {

            private User user;

            private UserBuilder() {
                user = new User();
            }

            public User.UserBuilder firstName(String firstName) {
                user.firstName = firstName;
                return this;
            }

            public User.UserBuilder lastName(String lastName) {
                user.lastName = lastName;
                return this;
            }
            public User.UserBuilder email(String email) {
                user.email = email;
                return this;
            }

            public User.UserBuilder password(String password) {
                user.password = password;
                return this;
            }
            public User bulid() {
                return user;
            }
        }
    }

