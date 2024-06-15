package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

public interface EnglishStudentService extends CrudService<EnglishStudent, Long> {
    Page<EnglishStudent> findEnglishStudentsByEnglishTeacher(Long englishTeacherId, Pageable pageable);
}
