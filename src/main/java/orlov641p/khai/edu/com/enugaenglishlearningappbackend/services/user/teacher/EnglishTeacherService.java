package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.teacher;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher.dto.request.EnglishTeacherCreateRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher.dto.request.EnglishTeacherUpdateRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

public interface EnglishTeacherService extends CrudService<EnglishTeacher, Long> {
    EnglishTeacher findByUser(User user);

    EnglishTeacher findByUserId(Long userId);

    EnglishTeacher createFromRequest(EnglishTeacherCreateRequest englishTeacherCreateRequest);

    EnglishTeacher updateFromRequest(EnglishTeacherUpdateRequest englishTeacherUpdateRequest);
}
