package events.project.service;

import events.project.model.User;
import events.project.model.UserDto;
import events.project.other.EventDtoToEventMapper;
import events.project.other.EventToEventDtoMapper;
import events.project.other.UserDtoToUserMapper;
import events.project.other.UserToUserDtoMapper;
import events.project.repositories.UserRepository;
import events.project.model.UserRole;
import events.project.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Service
public class UserServiceImpl implements UserService{

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private UserRepository userRepository;
    private UserRoleRepository roleRepository;
    

    private UserToUserDtoMapper toUserDto = new UserToUserDtoMapper();

    private UserDtoToUserMapper toUser = new UserDtoToUserMapper();
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void addWithDefaultRole(UserDto userDto) {
        User user = toUser.map(userDto);
        UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
        user.getRoles().add(defaultRole);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean isUserExist(UserDto userDto){
        User user = toUser.map(userDto);
        for (User u: userRepository.findAll())
            if (u.getEmail().equals(user.getEmail())){
                return true;}
        return false;
    };

    @Override
    public boolean isAuthenticated(Authentication authentication) {
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    @Override
    public void logoutUser(HttpSession httpSession) {
        if (httpSession != null) {
            httpSession.invalidate();
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
    }

    @Override
    public boolean login(String login, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        boolean isAuthenticated = isAuthenticated(authentication);
        if (isAuthenticated) {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
        }
        return isAuthenticated;
    }



}