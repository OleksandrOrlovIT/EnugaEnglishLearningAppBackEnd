package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.user.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.user.UserLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;

import java.util.Set;

@Profile("!test")
@Slf4j
@AllArgsConstructor
@Component
public class UserLoaderImpl implements UserLoader {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void loadUsers() {
        if(userService.getFirst() == null){
            saveUsers();
            log.info("Users were loaded ");
        } else {
            log.info("Users loading were skipped");
        }
    }

    public void saveUsers() {
        createUserIfNotExists("test1@email.com", "TestName1", "TestName1", "123",
                Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION));

        createUserIfNotExists("test2@email.com", "TestName2", "TestName2", "123",
                Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION, Role.ROLE_USER_WITH_SUBSCRIPTION));

        createUserIfNotExists("test3@email.com", "TestName3", "TestName3", "123",
                Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION, Role.ROLE_USER_WITH_SUBSCRIPTION,
                        Role.ROLE_ENGLISH_STUDENT_USER));

        createUserIfNotExists("test4@email.com", "TestName4", "TestName4", "123",
                Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION, Role.ROLE_USER_WITH_SUBSCRIPTION,
                        Role.ROLE_ENGLISH_STUDENT_USER, Role.ROLE_ENGLISH_TEACHER_USER));

        createUserIfNotExists("test5@email.com", "TestName5", "TestName5", "123",
                Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION, Role.ROLE_USER_WITH_SUBSCRIPTION,
                        Role.ROLE_ENGLISH_STUDENT_USER, Role.ROLE_ENGLISH_TEACHER_USER, Role.ROLE_ADMIN));
    }

    private void createUserIfNotExists(String email, String firstName, String lastName, String password, Set<Role> roles) {
        if (userService.getUserByEmail(email) == null) {
            User user = User.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .password(password)
                    .roles(roles)
                    .build();
            userService.create(user);
        } else {
            log.info("User with email {} already exists", email);
        }
    }
}
