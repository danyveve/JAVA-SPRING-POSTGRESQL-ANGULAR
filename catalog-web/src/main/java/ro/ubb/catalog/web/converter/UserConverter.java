package ro.ubb.catalog.web.converter;


import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.model.User;
import ro.ubb.catalog.core.model.UserRole;
import ro.ubb.catalog.web.dto.MovieDto;
import ro.ubb.catalog.web.dto.UserDto;

@Component
public class UserConverter extends AbstractConverterBaseEntity<User, UserDto> {
    @Override
    public User convertDtoToModel(UserDto userDto) {
        UserRole myRole;
        if(userDto.getRole().equals("ADMIN"))
            myRole = UserRole.ADMIN;
        else
            myRole = UserRole.CLIENT;
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .userName(userDto.getUsername())
                .password(userDto.getPassword())
                .userRole(myRole)
                .build();
        user.setId(userDto.getId());
        return user;
    }

    @Override
    public UserDto convertModelToDto(User user) {
        UserDto userDto = UserDto.builder()
               .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUserName())
                .password(user.getPassword())
                .role(user.getUserRole().toString())
                .build();
        userDto.setId(user.getId());
        return userDto;
    }

}
