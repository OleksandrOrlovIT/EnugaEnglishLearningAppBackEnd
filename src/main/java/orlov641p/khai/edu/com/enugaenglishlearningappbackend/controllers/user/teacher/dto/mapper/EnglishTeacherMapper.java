package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher.dto.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.mapper.EnglishStudentMapper;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher.dto.response.EnglishTeacherResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;

import java.util.List;
import java.util.stream.Collectors;

public class EnglishTeacherMapper {

    public static Page<EnglishTeacherResponse> convertEnglishTeacherPageToResponse(Page<EnglishTeacher> englishTeachers) {
        List<EnglishTeacherResponse> englishTeacherResponses = englishTeachers.getContent().stream()
                .map(EnglishTeacherMapper::convertEnglishTeacherToResponse)
                .collect(Collectors.toList());

        Pageable pageable = englishTeachers.getPageable();

        return new PageImpl<>(englishTeacherResponses, pageable, englishTeachers.getTotalElements());
    }

    public static EnglishTeacherResponse convertEnglishTeacherToResponse(EnglishTeacher englishTeacher) {
        return new EnglishTeacherResponse(englishTeacher);
    }
}
