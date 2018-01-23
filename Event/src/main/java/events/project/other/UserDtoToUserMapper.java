package events.project.other;

import events.project.model.Event;
import events.project.model.EventDto;
import events.project.model.User;
import events.project.model.UserDto;

public class UserDtoToUserMapper implements Mapper<UserDto,User> {
    @Override
    public User map(UserDto user) {
             return User.New().firstName(user.getFirstName()).
                lastName(user.getLastName()).email(user.getEmail()).
                password(user.getPassword()).bulid();
    }
}
