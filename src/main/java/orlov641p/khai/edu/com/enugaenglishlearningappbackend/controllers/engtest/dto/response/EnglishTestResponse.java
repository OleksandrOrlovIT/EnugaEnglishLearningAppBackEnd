package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.engtest.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class EnglishTestResponse {

    private Long id;

    private String testName;

    private List<Question> questions;

    public EnglishTestResponse(EnglishTest englishTest) {
        this.id = englishTest.getId();
        this.testName = englishTest.getTestName();
        this.questions = englishTest.getQuestions();
    }
}
