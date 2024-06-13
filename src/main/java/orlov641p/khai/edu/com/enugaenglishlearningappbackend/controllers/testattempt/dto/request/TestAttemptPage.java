package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class TestAttemptPage {

    public Long userId;

    public Integer pageSize;

    public Integer pageNumber;
}
