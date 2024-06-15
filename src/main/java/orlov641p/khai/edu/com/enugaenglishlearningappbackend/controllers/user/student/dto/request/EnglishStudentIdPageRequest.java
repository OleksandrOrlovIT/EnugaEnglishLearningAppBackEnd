package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnglishStudentIdPageRequest {
    private Long englishStudentId;
    private Long englishTeacherId;
    private Integer pageNumber;
    private Integer pageSize;
}
