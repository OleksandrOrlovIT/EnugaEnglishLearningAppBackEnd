package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request.TestAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.engtest.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

public interface EnglishTestService extends CrudService<EnglishTest, Long> {
    void addQuestion(Question question);

    void deleteQuestion(Question question);

    TestAttempt takeTheTest(TestAttemptRequest testAttemptRequest);
}
