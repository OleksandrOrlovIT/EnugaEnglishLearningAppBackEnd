package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
