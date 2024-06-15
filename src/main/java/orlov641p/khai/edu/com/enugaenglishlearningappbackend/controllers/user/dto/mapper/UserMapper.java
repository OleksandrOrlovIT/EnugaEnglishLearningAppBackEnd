package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.request.UserWithoutRolesRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.response.UserResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;

import java.util.List;
import java.util.stream.Collectors;

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

    public static Page<UserResponse> userPageToUserResponsePage(Page<User> users){
        List<UserResponse> userResponses = users.getContent().stream()
                .map(UserMapper::convertUserToUserResponse)
                .collect(Collectors.toList());

        Pageable pageable = users.getPageable();

        return new PageImpl<>(userResponses, pageable, users.getTotalElements());
    }

    public static UserResponse convertUserToUserResponse(User user){
        return new UserResponse(user);
    }
}
