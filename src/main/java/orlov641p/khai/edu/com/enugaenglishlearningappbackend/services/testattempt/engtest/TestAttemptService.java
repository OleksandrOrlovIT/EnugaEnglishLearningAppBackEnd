package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.engtest;

import org.springframework.data.domain.Page;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request.TestAttemptPage;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request.TestAttemptWithoutAnswers;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.engtest.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

import java.util.List;

public interface TestAttemptService extends CrudService<TestAttempt, Long> {
    List<TestAttempt> findTestAttemptsByUser(User user);

    Page<TestAttempt> findTestAttemptsPageByUser(TestAttemptPage testAttemptPage);

    Page<TestAttempt> findLastTestAttemptsPageByUserSortedByDate(TestAttemptPage testAttemptPage);

    TestAttempt findMaximumScoreAttempt(TestAttemptWithoutAnswers testAttempt);

    TestAttempt findLastAttemptScore(TestAttemptWithoutAnswers testAttempt);
}
