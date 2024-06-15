package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class UserWithoutRolesRequest {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
