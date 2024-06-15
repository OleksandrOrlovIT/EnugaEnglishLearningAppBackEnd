package orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.engteacher;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.user.UserSecurity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student.EnglishStudentService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.teacher.EnglishTeacherService;

@AllArgsConstructor
@Component
public class EnglishTeacherSecurity {
    private final UserSecurity userSecurity;
    private final EnglishTeacherService englishTeacherService;
    private final EnglishStudentService englishStudentService;

    public boolean checkIfLoggedUserIsRequestedTeacher(Long englishTeacherId){
        User loggedUser = userSecurity.getLoggedUser();

        if(loggedUser.getRoles().contains(Role.ROLE_ADMIN)){
            return true;
        }

        if(!loggedUser.getRoles().contains(Role.ROLE_ENGLISH_TEACHER_USER)){
            return false;
        }

        EnglishTeacher englishTeacher = englishTeacherService.findById(englishTeacherId);

        return englishTeacher.getUser().equals(loggedUser);
    }

    public boolean checkIfRequestedStudentIsTeachersStudent(Long studentId, Long englishTeacherId){
        User loggedUser = userSecurity.getLoggedUser();

        if(!loggedUser.getRoles().contains(Role.ROLE_ENGLISH_TEACHER_USER)){
            return false;
        }

        if(!checkIfLoggedUserIsRequestedTeacher(englishTeacherId)){
            return false;
        }

        EnglishTeacher englishTeacher = englishTeacherService.findById(englishTeacherId);
        EnglishStudent englishStudent = englishStudentService.findById(studentId);

        return englishStudent.getTeacher().equals(englishTeacher);
    }
}
