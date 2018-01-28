package events.project.mappers;

import events.project.modelEntity.User;
import events.project.modelDto.UserDto;
/**
 * Klasa mapująca obiekt User na obiekt UsetDto
 * @version 1.1
 */
public class UserToUserDtoMapper implements Mapper<User, UserDto> {
    @Override
    public UserDto map(User user) {
        if (user != null) {
            return UserDto.builder().firstName(user.getFirstName()).
                    lastName(user.getLastName()).email(user.getEmail()).build();
        } else return null;
    }
}
