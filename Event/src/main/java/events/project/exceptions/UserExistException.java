package events.project.exceptions;

import events.project.modelDto.UserDto;
/**
 * Wyjątek - dany użytkownik istnieje już w repozytorium
 * @version 1.1
 */
public class UserExistException extends RuntimeException {

    private UserDto user;
    public UserExistException(UserDto user) {
        this.user = user;
    }
    public UserDto getUser() {
        return user;
    }

}
