package events.project.service;

import events.project.model.User;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService {

    boolean isUserExist(User user);
    void addWithDefaultRole(User user);
    User findByEmail(String email);
    boolean isAuthenticated(Authentication authentication);
    void logoutUser(User sessionUser, HttpSession httpSession);
    boolean login(String login, String password, HttpServletRequest request);
}
