package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> retrieveUsers(){
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public User retrieveUserById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userService.create(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedUser);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        if(userService.findById(id) == null) {
            return null;
        }

        return userService.update(user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
