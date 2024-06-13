package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.request.TestAttemptPage;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.request.TestAttemptWithoutAnswers;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

import java.util.List;

public interface TestAttemptService extends CrudService<TestAttempt, Long> {
    List<TestAttempt> findTestAttemptsByUser(User user);

    Page<TestAttempt> findTestAttemptsPageByUser(TestAttemptPage testAttemptPage);

    TestAttempt findMaximumScoreAttempt(TestAttemptWithoutAnswers testAttempt);
}
