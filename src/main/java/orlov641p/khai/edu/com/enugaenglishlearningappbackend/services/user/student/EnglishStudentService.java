package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.request.EnglishStudentCreateRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.request.EnglishStudentUpdateRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

public interface EnglishStudentService extends CrudService<EnglishStudent, Long> {
    Page<EnglishStudent> findEnglishStudentsByEnglishTeacher(Long englishTeacherId, Pageable pageable);

    EnglishStudent createEnglishStudentFromRequest(EnglishStudentCreateRequest englishStudentCreateRequest);

    EnglishStudent updateEnglishStudentFromRequest(EnglishStudentUpdateRequest englishStudentUpdateRequest);

    EnglishStudent findByUser(User user);

    Page<EnglishStudent> getEnglishStudentsPage(Pageable pageable);
}
