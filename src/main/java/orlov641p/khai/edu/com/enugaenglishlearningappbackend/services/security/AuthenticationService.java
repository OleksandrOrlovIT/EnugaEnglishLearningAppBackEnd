package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth.dto.request.UserLogin;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth.dto.response.JwtAuthenticationResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth.dto.response.UserWithoutPassResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;

import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public JwtAuthenticationResponse signIn(UserLogin userLogin) {
        Objects.requireNonNull(userLogin, "Request user must not be null");

        try {
            User foundUser = userService.getUserByEmail(userLogin.getEmail());

            Objects.requireNonNull(foundUser, "User with email = " + userLogin.getEmail() + " not found");

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword());

            authenticationManager.authenticate(authenticationToken);

            var jwt = jwtService.generateToken(foundUser);

            UserWithoutPassResponse userResponse = new UserWithoutPassResponse(foundUser.getId(), foundUser.getEmail(),
                    foundUser.getFirstName(), foundUser.getLastName(), foundUser.getRoles());

            return new JwtAuthenticationResponse(jwt, userResponse);
        } catch (Exception ex) {
            log.error("Authentication failed: {}", ex.getMessage());
            throw ex;
        }
    }
}
