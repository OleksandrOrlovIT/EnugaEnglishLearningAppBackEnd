package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.response.UserResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;

@NoArgsConstructor
@Setter
@Getter
public class EnglishTeacherResponse {
    private Long id;
    private UserResponse user;

    public EnglishTeacherResponse(EnglishTeacher englishTeacher) {
        this.id = englishTeacher.getId();
        this.user = new UserResponse(englishTeacher.getUser());
    }
}