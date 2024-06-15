package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;

public interface EnglishStudentRepository extends JpaRepository<EnglishStudent, Long> {
    Page<EnglishStudent> findByTeacher(EnglishTeacher teacher, Pageable pageable);
}
