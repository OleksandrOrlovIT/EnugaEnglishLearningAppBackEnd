package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.user.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.user.UserLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student.EnglishStudentService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.teacher.EnglishTeacherService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Profile("!test")
@Slf4j
@AllArgsConstructor
@Component
public class UserLoaderImpl implements UserLoader {

    private final UserService userService;
    private final EnglishTeacherService englishTeacherService;
    private final EnglishStudentService englishStudentService;

    @Override
    public void loadUsers() {
        if (userService.getFirst() == null) {
            saveUsers();
            log.info("Users were loaded ");
        } else {
            log.info("Users loading were skipped");
        }

        if (englishTeacherService.getFirst() == null) {
            List<EnglishTeacher> englishTeachers = saveEnglishTeachers();
            log.info("English Teachers were loaded ");

            saveStudents(englishTeachers);
            log.info("English Students were loaded ");
        } else {
            log.info("English Teachers and English Students were skipped ");
        }
    }

    private void saveUsers() {
        createUser("test1@email.com", "TestName1", "TestName1",
                Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION));

        createUser("test2@email.com", "TestName2", "TestName2",
                Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION, Role.ROLE_USER_WITH_SUBSCRIPTION));

        createUser("test3@email.com", "TestName3", "TestName3",
                Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION, Role.ROLE_USER_WITH_SUBSCRIPTION,
                        Role.ROLE_ENGLISH_STUDENT_USER));

        createUser("test4@email.com", "TestName4", "TestName4",
                Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION, Role.ROLE_USER_WITH_SUBSCRIPTION,
                        Role.ROLE_ENGLISH_STUDENT_USER, Role.ROLE_ENGLISH_TEACHER_USER));

        createUser("admin@admin.com", "admin", "admin",
                Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION, Role.ROLE_USER_WITH_SUBSCRIPTION,
                        Role.ROLE_ENGLISH_STUDENT_USER, Role.ROLE_ENGLISH_TEACHER_USER, Role.ROLE_ADMIN));
    }

    private List<EnglishTeacher> saveEnglishTeachers(){
        Set<Role> teachersRoles = Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION, Role.ROLE_USER_WITH_SUBSCRIPTION,
                Role.ROLE_ENGLISH_STUDENT_USER, Role.ROLE_ENGLISH_TEACHER_USER);

        EnglishTeacher englishTeacher1 = EnglishTeacher.builder()
                .user(constructUser("englishTeacher1@email.com", "english1", "Teacher1", teachersRoles ))
                .build();

        EnglishTeacher englishTeacher2 = EnglishTeacher.builder()
                .user(constructUser("englishTeacher2@email.com", "english2", "Teacher2", teachersRoles ))
                .build();

        EnglishTeacher englishTeacher3 = EnglishTeacher.builder()
                .user(constructUser("englishTeacher3@email.com", "english3", "Teacher3", teachersRoles ))
                .build();

        englishTeacher1 = englishTeacherService.create(englishTeacher1);
        englishTeacher2 = englishTeacherService.create(englishTeacher2);
        englishTeacher3 = englishTeacherService.create(englishTeacher3);

        return List.of(englishTeacher1, englishTeacher2, englishTeacher3);
    }

    private void saveStudents(List<EnglishTeacher> englishTeachers) {
        Set<Role> studentRoles = Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION, Role.ROLE_USER_WITH_SUBSCRIPTION,
                Role.ROLE_ENGLISH_STUDENT_USER);

        List<EnglishStudent> englishStudents = new ArrayList<>();

        EnglishStudent student1Teacher1 = EnglishStudent.builder()
                .teacher(englishTeachers.get(0))
                .user(constructUser("student1teacher1@email.com", "student1", "teacher1",
                        studentRoles))
                .build();
        englishStudents.add(student1Teacher1);

        EnglishStudent student2Teacher1 = EnglishStudent.builder()
                .teacher(englishTeachers.get(0))
                .user(constructUser("student2teacher1@email.com", "student2", "teacher1",
                        studentRoles))
                .build();
        englishStudents.add(student2Teacher1);

        EnglishStudent student3Teacher1 = EnglishStudent.builder()
                .teacher(englishTeachers.get(0))
                .user(constructUser("student3teacher1@email.com", "student3", "teacher1",
                        studentRoles))
                .build();
        englishStudents.add(student3Teacher1);

        EnglishStudent student1Teacher2 = EnglishStudent.builder()
                .teacher(englishTeachers.get(1))
                .user(constructUser("student1teacher2@email.com", "student1", "teacher2",
                        studentRoles))
                .build();
        englishStudents.add(student1Teacher2);

        EnglishStudent student2Teacher2 = EnglishStudent.builder()
                .teacher(englishTeachers.get(1))
                .user(constructUser("student2teacher2@email.com", "student2", "teacher2",
                        studentRoles))
                .build();
        englishStudents.add(student2Teacher2);

        EnglishStudent student1Teacher3 = EnglishStudent.builder()
                .teacher(englishTeachers.get(2))
                .user(constructUser("student1teacher3@email.com", "student1", "teacher3",
                        studentRoles))
                .build();
        englishStudents.add(student1Teacher3);

        for(EnglishStudent englishStudent : englishStudents){
            englishStudentService.create(englishStudent);
        }
    }

    private User constructUser(String email, String firstName, String lastName, Set<Role> roles) {
        return User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password("123")
                .roles(roles)
                .build();
    }

    private void createUser(String email, String firstName, String lastName, Set<Role> roles) {
        User user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password("123")
                .roles(roles)
                .build();

        userService.create(user);
    }
}
