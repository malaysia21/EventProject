package events.project.users;
import events.project.controller.EventExistException;
import events.project.other.CustomErrorType;
import events.project.validation.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

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


//        if (eventService.isEventExist(event)) {
//            logger.error("Unable to create. An Event with name {} already exist", event.getName());
//            return new ResponseEntity(new CustomErrorType("Unable to create. Event with name " +
//                    event.getName() + " already exist."), HttpStatus.CONFLICT);
//        }

        userService.addWithDefaultRole(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
}
