package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth.dto.mapper;

import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth.dto.request.UserRegister;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;

import java.util.Set;

@Component
public class AuthUserMapper {
    public User convertUserRegisterToUser(UserRegister userRegister){
        return User.builder()
                .email(userRegister.getEmail())
                .firstName(userRegister.getFirstName())
                .lastName(userRegister.getLastName())
                .password(userRegister.getPassword())
                .roles(Set.of(Role.USER_WITHOUT_SUBSCRIPTION))
                .build();
    }
}