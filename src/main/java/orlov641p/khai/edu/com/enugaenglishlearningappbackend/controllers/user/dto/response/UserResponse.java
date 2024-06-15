package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;

import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
public class UserResponse {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private Set<Role> roles;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roles = user.getRoles();
    }
}