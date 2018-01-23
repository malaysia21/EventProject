package events.project.users;
import events.project.controller.EventExistException;
import events.project.other.CustomErrorType;
import events.project.validation.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@Controller
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }


    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<CustomErrorType> userExist(UserExistException e)
    {
        String email = e.getUser().getEmail();
        CustomErrorType error = new CustomErrorType( "Unable to create. Email " +
                email + " already exist.");
        return new ResponseEntity<CustomErrorType>(error, HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult result, UriComponentsBuilder ucBuilder) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }
        if (userService.isUserExist(user))
        {throw new UserExistException(user);}

        userService.addWithDefaultRole(user);

        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/logedUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(Authentication auth) {
        String name = auth.getName();
        String byEmail = userService.findByEmail(name).getEmail();
        return new ResponseEntity<String>(byEmail, HttpStatus.OK);
    }

//    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Principal login(Principal user) {
//        return user;
//    }

}
