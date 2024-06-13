package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithoutPassResponse {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private Set<Role> roles;
}
