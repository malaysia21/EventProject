package events.project.users;

import events.project.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private UserRepository userRepository;
    private UserRoleRepository roleRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public void addWithDefaultRole(User user) {
        UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
        user.getRoles().add(defaultRole);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isUserExist(User user){
        for (User u: userRepository.findAll())
            if (u.getEmail().equals(user.getEmail())){
                return true;}
        return false;
    };


}