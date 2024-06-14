package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class WordModuleAttemptRequest {
    private Long userId;

    private Long wordModuleId;

    private Map<Long, String> answers;
}
