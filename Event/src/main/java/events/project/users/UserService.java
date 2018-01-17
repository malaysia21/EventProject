package events.project.users;

public interface UserService {

    boolean isUserExist(User user);
    void addWithDefaultRole(User user);
    User findByEmail(String email);
}
