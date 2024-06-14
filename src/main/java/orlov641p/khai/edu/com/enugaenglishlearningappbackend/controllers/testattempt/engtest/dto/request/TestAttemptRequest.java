package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class TestAttemptRequest {

    private Long userId;

    private Long englishTestId;

    private Map<Long, String> answers;
}
