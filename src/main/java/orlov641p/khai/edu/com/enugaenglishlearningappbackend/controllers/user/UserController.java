package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.mapper.UserMapper;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.request.UserWithoutRolesRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.response.UserResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.IsAdminOrSelf;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserResponse> retrieveUsers(){
        List<User> users = userService.findAll();

        List<UserResponse> responses = new ArrayList<>();

        for(User user : users){
            responses.add(new UserResponse(user));
        }

        return responses;
    }

    @GetMapping("/user/{id}")
    @IsAdminOrSelf
    public UserResponse retrieveUserById(@PathVariable Long id){
        return new UserResponse(userService.findById(id));
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user){
        User savedUser = userService.create(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(new UserResponse(savedUser));
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody User user){
        if(userService.findById(id) == null) {
            return null;
        }

        return new UserResponse(userService.update(user));
    }

    @PutMapping("/user/{id}/without-roles")
    @IsAdminOrSelf
    public UserResponse updateUserWithoutRoles(@PathVariable Long id, @RequestBody UserWithoutRolesRequest request){
        if(userService.findById(id) == null) {
            return null;
        }

        User user = UserMapper.convertUserWithoutRolesRequestToUser(request);

        return new UserResponse(userService.update(user));
    }

    @DeleteMapping("/user/{id}")
    @IsAdminOrSelf
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}