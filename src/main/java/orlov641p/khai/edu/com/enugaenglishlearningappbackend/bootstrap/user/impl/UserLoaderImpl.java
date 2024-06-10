package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.user.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
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

    @Override
    public void run(String... args) throws Exception {
        if(userService.getFirst() == null){
            loadUsers();
            log.info("Users were loaded ");
        } else {
            log.info("Users loading were skipped");
        }
    }

    @Override
    public void loadUsers() {
        User withoutSubUser = User.builder()
                .email("test1@email.com")
                .firstName("TestName1")
                .lastName("TestName1")
                .password("123")
                .roles(Set.of(Role.USER_WITHOUT_SUBSCRIPTION))
                .build();

        User withSubUser = User.builder()
                .email("test2@email.com")
                .firstName("TestName2")
                .lastName("TestName2")
                .password("123")
                .roles(Set.of(Role.USER_WITHOUT_SUBSCRIPTION, Role.USER_WITH_SUBSCRIPTION))
                .build();

        User englishStudent = User.builder()
                .email("test3@email.com")
                .firstName("TestName3")
                .lastName("TestName3")
                .password("123")
                .roles(Set.of(Role.USER_WITHOUT_SUBSCRIPTION, Role.USER_WITH_SUBSCRIPTION, Role.ENGLISH_STUDENT_USER))
                .build();

        User englishTeacher = User.builder()
                .email("test4@email.com")
                .firstName("TestName4")
                .lastName("TestName4")
                .password("123")
                .roles(Set.of(Role.USER_WITHOUT_SUBSCRIPTION, Role.USER_WITH_SUBSCRIPTION, Role.ENGLISH_STUDENT_USER,
                        Role.ENGLISH_TEACHER_USER))
                .build();

        User admin = User.builder()
                .email("test5@email.com")
                .firstName("TestName5")
                .lastName("TestName5")
                .password("123")
                .roles(Set.of(Role.USER_WITHOUT_SUBSCRIPTION, Role.USER_WITH_SUBSCRIPTION, Role.ENGLISH_STUDENT_USER,
                        Role.ENGLISH_TEACHER_USER, Role.ADMIN))
                .build();

        userService.create(withoutSubUser);
        userService.create(withSubUser);
        userService.create(englishStudent);
        userService.create(englishTeacher);
        userService.create(admin);
    }
}
