package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.mapper;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.request.UserWithoutRolesRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;

public class UserMapper {
    public static User convertUserWithoutRolesRequestToUser(UserWithoutRolesRequest userWithoutRolesRequest){
        return User.builder()
                .id(userWithoutRolesRequest.getId())
                .email(userWithoutRolesRequest.getEmail())
                .firstName(userWithoutRolesRequest.getFirstName())
                .lastName(userWithoutRolesRequest.getLastName())
                .password(userWithoutRolesRequest.getPassword())
                .build();
    }
}
