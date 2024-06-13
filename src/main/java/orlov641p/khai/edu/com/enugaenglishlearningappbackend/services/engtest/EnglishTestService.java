package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.request.TestAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

import java.util.Map;

public interface EnglishTestService extends CrudService<EnglishTest, Long> {
    void addQuestion(Question question);

    void deleteQuestion(Question question);

    TestAttempt takeTheTest(TestAttemptRequest testAttemptRequest);
}
