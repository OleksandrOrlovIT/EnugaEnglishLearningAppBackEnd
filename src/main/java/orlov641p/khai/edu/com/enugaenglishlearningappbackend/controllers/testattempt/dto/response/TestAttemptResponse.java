package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.TestAttempt;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class TestAttemptResponse {

    private Long id;

    private Long userId;

    private Long englishTestId;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime attemptDate;

    private Integer rightAnswers;

    private Map<Long, String> wrongAnswers;

    private Double successPercentage;

    public TestAttemptResponse(TestAttempt testAttempt){
        this.id = testAttempt.getId();
        this.userId = testAttempt.getUser().getId();
        this.englishTestId = testAttempt.getEnglishTest().getId();
        this.attemptDate = testAttempt.getAttemptDate();
        this.rightAnswers = testAttempt.getRightAnswers();
        this.wrongAnswers = testAttempt.getWrongAnswers();
        this.successPercentage = testAttempt.getSuccessPercentage();
    }
}
