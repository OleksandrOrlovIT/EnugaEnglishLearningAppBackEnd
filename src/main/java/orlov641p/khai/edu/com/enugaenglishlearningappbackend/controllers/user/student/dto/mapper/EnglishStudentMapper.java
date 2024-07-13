package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.mapper.WordModuleAttemptMapper;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.response.WordModuleAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.response.EnglishStudentResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;

import java.util.List;
import java.util.stream.Collectors;

public class EnglishStudentMapper {

    public static Page<EnglishStudentResponse> convertEnglishStudentPageToResponse(Page<EnglishStudent> englishStudents){
        List<EnglishStudentResponse> englishStudentResponses = englishStudents.getContent().stream()
                .map(EnglishStudentMapper::convertEnglishStudentToResponse)
                .collect(Collectors.toList());

        Pageable pageable = englishStudents.getPageable();

        return new PageImpl<>(englishStudentResponses, pageable, englishStudents.getTotalElements());
    }

    public static EnglishStudentResponse convertEnglishStudentToResponse(EnglishStudent englishStudent){
        return new EnglishStudentResponse(englishStudent);
    }

    public static List<EnglishStudentResponse> convertEnglishStudentListToEnglishStudentResponseList
            (List<EnglishStudent> englishStudents){
        return englishStudents.stream().map(EnglishStudentMapper::convertEnglishStudentToResponse).toList();
    }
}
