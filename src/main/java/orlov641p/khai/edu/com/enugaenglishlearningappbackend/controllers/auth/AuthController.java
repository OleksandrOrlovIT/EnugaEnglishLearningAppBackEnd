package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth.dto.mapper.AuthUserMapper;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth.dto.request.UserLogin;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth.dto.request.UserRegister;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.auth.dto.response.JwtAuthenticationResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.security.AuthenticationService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final AuthUserMapper authUserMapper;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody @Validated UserRegister userRegister) {
        User savedUser = authUserMapper.convertUserRegisterToUser(userRegister);
        savedUser = userService.create(savedUser);

        return ResponseEntity.ok("User has been created = " + savedUser.getId());
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse loginUser(@RequestBody @Validated UserLogin UserLogin) {
        return authenticationService.signIn(UserLogin);
    }
}
