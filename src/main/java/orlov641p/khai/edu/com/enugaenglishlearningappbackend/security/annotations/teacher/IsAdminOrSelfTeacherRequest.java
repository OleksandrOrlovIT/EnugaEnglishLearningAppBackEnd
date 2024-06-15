package orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.teacher;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@englishTeacherSecurity.checkIfLoggedUserIsRequestedTeacher(#request.englishTeacherId)")
public @interface IsAdminOrSelfTeacherRequest {
}