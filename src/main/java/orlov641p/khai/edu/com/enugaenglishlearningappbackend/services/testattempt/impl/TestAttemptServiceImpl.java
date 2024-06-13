package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.engtest.QuestionRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.testattempt.TestAttemptRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.QuestionService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.TestAttemptService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class TestAttemptServiceImpl implements TestAttemptService {

    private final TestAttemptRepository testAttemptRepository;
    private final QuestionService questionService;

    @Override
    public List<TestAttempt> findAll() {
        return testAttemptRepository.findAll();
    }

    @Override
    public TestAttempt findById(Long id) {
        checkTestAttemptIdNull(id);

        return testAttemptRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TestAttempt with id = " + id + " doesn't exist"));
    }

    @Override
    public TestAttempt create(TestAttempt testAttempt) {
        checkTestAttemptNull(testAttempt);

        List<Question> questions = getEnglishTestQuestions(testAttempt);

        int questionsSize = questions == null ? 0 : questions.size();

        int rightAnswers = testAttempt.getRightAnswers() == null ? 0 : testAttempt.getRightAnswers();

        int wrongAnswersSize = testAttempt.getWrongAnswers() == null ? 0 : testAttempt.getWrongAnswers().size();

        if(rightAnswers + wrongAnswersSize != questionsSize) {
            throw new IllegalArgumentException("Wrong amount of rightAnswers and wrongAnswers");
        }

        double successPercentage = !questions.isEmpty() ?
                ((double) rightAnswers / questionsSize) * 100 : 0.0;

        testAttempt.setSuccessPercentage(successPercentage);

        if (testAttempt.getAttemptDate() == null) {
            ZoneId kievZone = ZoneId.of("Europe/Kiev");
            testAttempt.setAttemptDate(ZonedDateTime.now(kievZone).toLocalDateTime());
        }

        return testAttemptRepository.save(testAttempt);
    }

    @Override
    public TestAttempt update(TestAttempt testAttempt) {
        checkTestAttemptNull(testAttempt);

        findById(testAttempt.getId());

        return testAttemptRepository.save(testAttempt);
    }

    @Override
    public void delete(TestAttempt testAttempt) {
        checkTestAttemptNull(testAttempt);

        testAttemptRepository.delete(testAttempt);
    }

    @Override
    public void deleteById(Long id) {
        checkTestAttemptIdNull(id);

        testAttemptRepository.deleteById(id);
    }

    @Override
    public TestAttempt getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<TestAttempt> testAttempts = testAttemptRepository.findAll(pageable);

        return testAttempts.hasContent() ? testAttempts.getContent().get(0) : null;
    }

    private void checkTestAttemptNull(TestAttempt testAttempt){
        if(testAttempt == null){
            throw new IllegalArgumentException("TestAttempt can't be null");
        }
        if(testAttempt.getUser() == null){
            throw new IllegalArgumentException("TestAttempt user can't be null");
        }
        if(testAttempt.getEnglishTest() == null){
            throw new IllegalArgumentException("TestAttempt english test can't be null");
        }
    }

    private void checkTestAttemptIdNull(Long id){
        if(id == null){
            throw new IllegalArgumentException("TestAttempt id can`t be null");
        }
    }

    private List<Question> getEnglishTestQuestions(TestAttempt testAttempt){
        EnglishTest englishTest = testAttempt.getEnglishTest();

        if(englishTest == null){
            throw new IllegalArgumentException("TestAttempt EnglishTest can't be null");
        }

        List<Question> questions = questionService.getQuestionsByEnglishTestId(englishTest.getId());

        if(questions == null){
            throw new IllegalArgumentException("TestAttempt EnglishTest questions can't be null");
        }

        return questions;
    }
}
