package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;

public interface EnglishTeacherRepository extends JpaRepository<EnglishTeacher, Long> {
    EnglishTeacher findByUser(User user);
}
