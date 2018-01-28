package events.project.mappers;

import events.project.modelEntity.User;
import events.project.modelDto.UserDto;
/**
 * Klasa mapująca obiekt UserDto na obiekt User
 * @version 1.1
 */
public class UserDtoToUserMapper implements Mapper<UserDto,User> {
    @Override
    public User map(UserDto user) {
             return User.builder().firstName(user.getFirstName()).
                lastName(user.getLastName()).email(user.getEmail()).
                password(user.getPassword()).build();

    }
}
