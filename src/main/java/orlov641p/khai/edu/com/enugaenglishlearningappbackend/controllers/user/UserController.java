package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.request.UserPageRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.request.UserWithoutRolesRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.response.UserResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.user.IsAdminOrSelf;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;

import java.net.URI;
import java.util.List;

import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.mapper.UserMapper.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserResponse> retrieveUsers(){
        return userListToUserResponseList(userService.findAll());
    }

    @PostMapping("/users/page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<UserResponse> retrieveUsersPage(@RequestBody UserPageRequest userPageRequest){
        Pageable pageable = PageRequest.of(userPageRequest.getPageNumber(), userPageRequest.getPageSize());

        Page<User> users = userService.getUserPage(pageable);

        return userPageToUserResponsePage(users);
    }

    @GetMapping("/user/{id}")
    @IsAdminOrSelf
    public UserResponse retrieveUserById(@PathVariable Long id){
        return convertUserToUserResponse(userService.findById(id));
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user){
        User savedUser = userService.create(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(convertUserToUserResponse(savedUser));
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody User user){
        if(userService.findById(id) == null) {
            return null;
        }

        return convertUserToUserResponse(userService.update(user));
    }

    @PutMapping("/user/{id}/without-roles")
    @IsAdminOrSelf
    public UserResponse updateUserWithoutRoles(@PathVariable Long id, @RequestBody UserWithoutRolesRequest request){
        if(userService.findById(id) == null) {
            return null;
        }

        User user = convertUserWithoutRolesRequestToUser(request);

        return convertUserToUserResponse(userService.updateUserWithoutRoles(user));
    }

    @PutMapping("/user/{id}/upgrade-account")
    @PreAuthorize("!hasRole('ROLE_USER_WITH_SUBSCRIPTION')")
    public UserResponse upgradeUserSubscription(@PathVariable Long id){
        User user = userService.findById(id);

        if(user == null) {
            return null;
        }

        return convertUserToUserResponse(userService.upgradeUserSubscription(user));
    }

    @DeleteMapping("/user/{id}")
    @IsAdminOrSelf
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}