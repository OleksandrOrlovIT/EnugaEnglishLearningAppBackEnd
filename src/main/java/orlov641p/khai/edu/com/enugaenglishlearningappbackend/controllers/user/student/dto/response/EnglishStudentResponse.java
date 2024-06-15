package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.response.UserResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;

@NoArgsConstructor
@Setter
@Getter
public class EnglishStudentResponse {

    private Long id;
    private Long teacherId;
    private UserResponse user;

    public EnglishStudentResponse(EnglishStudent englishStudent) {
        this.id = englishStudent.getId();
        this.teacherId = englishStudent.getTeacher().getId();
        this.user = new UserResponse(englishStudent.getUser());
    }
}