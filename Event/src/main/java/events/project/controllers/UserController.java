package events.project.controllers;

import events.project.exceptions.UserExistException;
import events.project.modelDto.UserDto;
import events.project.modelDto.UserLogin;
import events.project.services.UserServiceImpl;
import events.project.validation.ValidationErrorBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Controller dla klasy User
 *
 * @version 1.1
 */
@Log4j2
@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }


    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void userExist(UserExistException e) {
        String email = e.getUser().getEmail();
        log.debug("Unable to create. Email " + email + " already exist.");
    }

    /**
     * Rejestracja użytkownika
     *
     * @param user   dane nowego użytkownika
     * @param result BindingResult
     * @throws UserExistException
     */
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addUser(@Valid @RequestBody UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }
        if (userService.isUserExist(user)) {
            throw new UserExistException(user);
        }
        userService.addWithDefaultRole(user);
        return ResponseEntity.ok().build();
    }

    /**
     * Pobranie informacji o zalogowanym użytkowniku
     *
     * @param auth autentykacja użytkownika
     * @return login użytkownika
     */
    @GetMapping(value = "/loggedUser")
    public ResponseEntity login(Authentication auth) {
        String name = auth.getName();
        return ResponseEntity.ok(name);
    }

    /**
     * Wylogowanie użytkownika
     */
    @GetMapping(value = "/logoutUser")
    public ResponseEntity logout(HttpServletRequest httpRequest) {
        userService.logoutUser(httpRequest.getSession(false));
        return ResponseEntity.noContent().build();
    }

    /**
     * Zalogowanie użytkownika - test
     *
     * @param login    login użytkownika
     * @param password hasło użytkownika
     */
    @GetMapping(value = "/logUserTest")
    public ResponseEntity loginTest(@RequestParam String login, @RequestParam String password) {
        boolean user = userService.login(login, password);
        if (user) {
            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Zalogowanie użytkownika
     *
     * @param user   dane logowania
     * @param result BindingResult
     */
    @RequestMapping(value = "/logUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@Valid @RequestBody UserLogin user, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(result));
        }
        boolean login = userService.login(user.getLogin(), user.getPassword());
        if (login) {
            request.getSession(true);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}


