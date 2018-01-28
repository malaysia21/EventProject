package events.project.services;

import events.project.modelEntity.User;
import events.project.modelDto.UserDto;
import events.project.mappers.UserDtoToUserMapper;
import events.project.mappers.UserToUserDtoMapper;
import events.project.repositories.UserRepository;
import events.project.modelEntity.UserRole;
import events.project.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Servis dla encji User
 * @version 1.1
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * rola dla wszystkich użytkowników
     */
    private static final String DEFAULT_ROLE = "ROLE_USER";
    private UserRepository userRepository;
    private UserRoleRepository roleRepository;
    private UserDtoToUserMapper toUser = new UserDtoToUserMapper();

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setRoleRepository(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Autowired
    private DaoAuthenticationProvider authenticationProvider;

    /**
     * dodanie użytkownika
     * @param userDto dane nowego użytkownika
     */
    @Override
    public void addWithDefaultRole(UserDto userDto) {
        User user = toUser.map(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
        user.getRoles().add(defaultRole);
        userRepository.save(user);
    }

    /**
     * wyszukwanie użytkownika po loginie
     * @param email email użytkownika (login)
     * @return użytkownik
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * sprawdzenie czy użytkownik o danym loginie już istnieje
     * @param userDto dane nowego użytkownika
     * @return true - użytkownik istnieje, false - użytkownik nie istnieje
     */
    @Override
    public boolean isUserExist(UserDto userDto) {
        User user = toUser.map(userDto);
        for (User u : userRepository.findAll())
            if (u.getEmail().equals(user.getEmail())) {
                return true;}
        return false;
    }

    /**
     * wyczyszczenie sesji i konteksu bezpieczeństwo
     * @param httpSession sesja zalogowanego użytkownika
     */
    @Override
    public void logoutUser(HttpSession httpSession) {
        if (httpSession != null) {
            httpSession.invalidate();
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
    }

    /**
     * logowanie użytkownika (autentykacja)
     * @param login
     * @param password
     * true - poprawnya autentykacja, false - błędna autentykacja
     */
    @Override
    public boolean login(String login, String password) {
        Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        boolean isAuthenticated = isAuthenticated(authentication);
        if (isAuthenticated) {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
        }
        return isAuthenticated;
    }

    @Override
    public boolean isAuthenticated(Authentication authentication) {
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

}