package events.project.other;

import events.project.model.Event;
import events.project.model.EventDto;
import events.project.model.User;
import events.project.model.UserDto;

public class UserToUserDtoMapper implements Mapper<User, UserDto> {
    @Override
    public UserDto map(User user)  {
        if(user==null){
            return null;
        }

        return UserDto.New().firstName(user.getFirstName()).
                lastName(user.getLastName()).email(user.getEmail()).bulid();
    }
}
