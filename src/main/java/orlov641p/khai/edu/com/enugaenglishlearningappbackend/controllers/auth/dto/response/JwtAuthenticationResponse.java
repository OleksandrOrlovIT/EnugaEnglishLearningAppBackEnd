package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
    private UserWithoutPassResponse userWithoutPassResponse;
}