package events.project.controller;

import events.project.model.User;

public class UserExistException extends RuntimeException {

    private User user;

    public UserExistException(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
