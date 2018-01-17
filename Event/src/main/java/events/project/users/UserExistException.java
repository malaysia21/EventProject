package events.project.users;

import events.project.model.Event;

public class UserExistException extends RuntimeException {

    private User user;

    public UserExistException(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
