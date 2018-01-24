package events.project.controller;
import events.project.model.UserDto;
import events.project.other.CustomErrorType;
import events.project.model.User;
import events.project.service.UserServiceImpl;
import events.project.validation.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }


    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<CustomErrorType> userExist(UserExistException e) {
        String email = e.getUser().getEmail();
        CustomErrorType error = new CustomErrorType("Unable to create. Email " +
                email + " already exist.");
        return new ResponseEntity<CustomErrorType>(error, HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto user, BindingResult result, UriComponentsBuilder ucBuilder) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }
        if (userService.isUserExist(user)) {
            throw new UserExistException(user);
        }
        userService.addWithDefaultRole(user);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/loggedUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(Authentication auth) {
        String name = auth.getName();
        return new ResponseEntity<String>(name, HttpStatus.OK);
    }


    @GetMapping(value = "/logoutUser", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity logout(HttpServletRequest httpRequest) {
        userService.logoutUser(httpRequest.getSession(false));
        return noContent().build();
    }


    @GetMapping(value = "/logUserTest", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginTest(@RequestParam String name, @RequestParam String password) {
        boolean login = userService.login(name, password);
        if (login = true) {
            return new ResponseEntity<String>(HttpStatus.OK);
        } else return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
    }



    @PostMapping(value = "/logUser", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody String name, String password ) {
        boolean login = userService.login(name, password);
        if (login == true) {
            return new ResponseEntity<String>(HttpStatus.OK);
        } else return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
    }

}


