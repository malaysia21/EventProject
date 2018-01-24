package events.project.controller;

import events.project.model.User;
import events.project.model.UserDto;

public class UserExistException extends RuntimeException {

    private UserDto user;

    public UserExistException(UserDto user) {
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }

}
