package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnglishStudentCreateRequest {
    private Long englishStudentId;
    private Long userId;
    private Long englishTeacherId;
}