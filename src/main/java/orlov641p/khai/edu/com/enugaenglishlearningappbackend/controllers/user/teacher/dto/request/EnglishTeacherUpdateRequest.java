package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnglishTeacherUpdateRequest {
    private Long englishTeacherId;
    private Long userId;
}
