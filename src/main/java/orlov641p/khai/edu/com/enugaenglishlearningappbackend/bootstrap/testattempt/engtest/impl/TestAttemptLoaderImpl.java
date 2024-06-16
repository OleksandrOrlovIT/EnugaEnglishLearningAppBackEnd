package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.testattempt.engtest.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.testattempt.engtest.TestAttemptLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.engtest.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;
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
        List<EnglishTest> englishTests = englishTestService.findAll();

        for (EnglishTest test : englishTests) {
            List<Question> questions = questionService.getQuestionsByEnglishTestId(test.getId());
            Map<Integer, Map<Long, String>> cache = cacheWrongAnswers(questions);

            int times = 1;

            for (User user : userService.getUserPage(PageRequest.of(0, 10))) {
                if (times == 4) {
                    times = 1;
                }

                loadTestAttempt(test, user, times, questions, cache);
                times++;
            }
        }
    }

    private void loadTestAttempt(EnglishTest englishTest, User user, int makeAttempts, List<Question> questions,
                                 Map<Integer, Map<Long, String>> cache) {
        saveTestAttempt(englishTest, user, questions.size() - cache.get(1).size(), cache.get(1));

        if(makeAttempts >= 2){
            saveTestAttempt(englishTest, user, questions.size() - cache.get(2).size(), cache.get(2));

            if(makeAttempts == 3){
                saveTestAttempt(englishTest, user, questions.size() - cache.get(3).size(), cache.get(3));
            }
        }
    }

    private void saveTestAttempt(EnglishTest englishTest, User user, int rightAnswers, Map<Long, String> wrongAnswers) {
        TestAttempt testAttempt = TestAttempt.builder()
                .englishTest(englishTest)
                .user(user)
                .rightAnswers(rightAnswers)
                .wrongAnswers(wrongAnswers)
                .build();

        testAttemptService.create(testAttempt);
    }

    private Map<Integer, Map<Long, String>> cacheWrongAnswers(List<Question> questions) {
        Map<Integer, Map<Long, String>> res = new HashMap<>();

        Map<Long, String> wrongAnswers1 = new HashMap<>();

        for (Question question : questions) {
            wrongAnswers1.put(question.getId(), question.getAnswer() + "asd");
        }

        res.put(1, wrongAnswers1);
        res.put(2, new HashMap<>());

        Map<Long, String> wrongAnswers3 = new HashMap<>(wrongAnswers1);

        for (int i = 0; i < questions.size() / 2; i++) {
            wrongAnswers3.remove(questions.get(i).getId());
        }

        res.put(3, wrongAnswers3);

        return res;
    }
}