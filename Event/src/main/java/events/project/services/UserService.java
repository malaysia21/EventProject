package events.project.services;

import events.project.modelEntity.User;
import events.project.modelDto.UserDto;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpSession;

public interface UserService {

    boolean isUserExist(UserDto user);

    void addWithDefaultRole(UserDto user);

    User findByEmail(String email);

    boolean isAuthenticated(Authentication authentication);

    void logoutUser(HttpSession httpSession);

    boolean login(String login, String password);
}
