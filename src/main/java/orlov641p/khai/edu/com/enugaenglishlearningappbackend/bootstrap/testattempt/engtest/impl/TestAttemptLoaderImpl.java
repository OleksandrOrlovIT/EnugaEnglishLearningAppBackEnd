package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.testattempt.engtest.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.testattempt.engtest.TestAttemptLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.engtest.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.EnglishTestService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.QuestionService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.engtest.TestAttemptService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("!test")
@Slf4j
@AllArgsConstructor
@Component
public class TestAttemptLoaderImpl implements TestAttemptLoader {

    private final TestAttemptService testAttemptService;
    private final EnglishTestService englishTestService;
    private final QuestionService questionService;
    private final UserService userService;

    @Override
    public void loadTestAttempts() {
        if (testAttemptService.getFirst() == null) {
            saveTestAttempts();
            log.info("TestAttempts were loaded");
        } else {
            log.info("TestAttempts loading were skipped");
        }
    }

    private void saveTestAttempts() {
        User admin = userService.getUserByEmail("admin@admin.com");
        EnglishTest test = englishTestService.getFirst();

        List<Question> questions = questionService.getQuestionsByEnglishTestId(test.getId());

        Map<Long, String> wrongAnswers1 = new HashMap<>();
        for (Question question : questions) {
            wrongAnswers1.put(question.getId(), question.getAnswer() + "asd");
        }

        Map<Long, String> wrongAnswers2 = new HashMap<>(wrongAnswers1);
        Map<Long, String> wrongAnswers3 = new HashMap<>(wrongAnswers1);

        TestAttempt testAttempt1 = TestAttempt.builder()
                .englishTest(test)
                .user(admin)
                .rightAnswers(questions.size())
                .build();

        TestAttempt testAttempt2 = TestAttempt.builder()
                .englishTest(test)
                .user(admin)
                .wrongAnswers(wrongAnswers2)
                .build();

        int rightAnswers = 0;
        for (int i = 0; i < questions.size() / 2; i++) {
            wrongAnswers3.remove(questions.get(i).getId());
            rightAnswers++;
        }

        TestAttempt testAttempt3 = TestAttempt.builder()
                .englishTest(test)
                .user(admin)
                .rightAnswers(rightAnswers)
                .wrongAnswers(wrongAnswers3)
                .build();

        testAttemptService.create(testAttempt1);
        testAttemptService.create(testAttempt2);
        testAttemptService.create(testAttempt3);
    }

}