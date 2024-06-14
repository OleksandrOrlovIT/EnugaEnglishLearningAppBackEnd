package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class WordModuleAttemptResponse {
    private Long id;

    private Long userId;

    private Long wordModuleId;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime attemptDate;

    private Integer rightAnswers;

    private Map<Long, String> wrongAnswers;

    private Double successPercentage;

    public WordModuleAttemptResponse(WordModuleAttempt wordModuleAttempt){
        this.id = wordModuleAttempt.getId();
        this.userId = wordModuleAttempt.getUser().getId();
        this.wordModuleId = wordModuleAttempt.getWordModule().getId();
        this.attemptDate = wordModuleAttempt.getAttemptDate();
        this.rightAnswers = wordModuleAttempt.getRightAnswers();
        this.wrongAnswers = wordModuleAttempt.getWrongAnswers();
        this.successPercentage = wordModuleAttempt.getSuccessPercentage();
    }
}
