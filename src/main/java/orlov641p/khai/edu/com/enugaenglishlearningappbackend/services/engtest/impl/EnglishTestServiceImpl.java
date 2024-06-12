package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.engtest.EnglishTestRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.EnglishTestService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.QuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class EnglishTestServiceImpl implements EnglishTestService {

    private final EnglishTestRepository englishTestRepository;
    private final QuestionService questionService;

    public EnglishTestServiceImpl(EnglishTestRepository englishTestRepository, QuestionService questionService) {
        this.englishTestRepository = Objects.requireNonNull(englishTestRepository, "EnglishTestRepository cannot be null");
        this.questionService = Objects.requireNonNull(questionService, "QuestionService cannot be null");
    }

    @Override
    public List<EnglishTest> findAll() {
        return englishTestRepository.findAll();
    }

    @Override
    public EnglishTest findById(Long id) {
        Objects.requireNonNull(id, "EnglishTest id cannot be null");

        EnglishTest englishTest = englishTestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EnglishTest with id = " + id + " doesn't exist"));
        englishTest.setQuestions(questionService.getQuestionsByEnglishTestId(englishTest.getId()));

        return englishTest;
    }

    @Override
    @Transactional
    public EnglishTest create(EnglishTest englishTest) {
        validateEnglishTestForCreation(englishTest);

        englishTest = englishTestRepository.save(englishTest);

        if(englishTest.getQuestions() != null) {
            for (Question question : englishTest.getQuestions()) {
                question.setEnglishTest(englishTest);
            }

            List<Question> savedQuestions = questionService.createAll(englishTest.getQuestions());

            englishTest.setQuestions(savedQuestions);
        }

        return englishTest;
    }

    @Override
    @Transactional
    public EnglishTest update(EnglishTest englishTest) {
        validateEnglishTestForUpdate(englishTest);

        EnglishTest oldEnglishTest = findById(englishTest.getId());

        if(oldEnglishTest.getQuestions() != null && !oldEnglishTest.getQuestions().isEmpty()) {
            removeDeletedQuestions(englishTest, oldEnglishTest);
        }

        if(englishTest.getQuestions() != null && !englishTest.getQuestions().isEmpty()) {
            updateOrAddQuestions(englishTest);
        }

        return englishTestRepository.save(englishTest);
    }

    private void removeDeletedQuestions(EnglishTest updatedEnglishTest, EnglishTest oldEnglishTest) {
        List<Question> oldQuestions = oldEnglishTest.getQuestions();
        oldQuestions.removeAll(updatedEnglishTest.getQuestions());

        for (Question question : oldQuestions) {
            questionService.deleteById(question.getId());
        }
    }

    private void updateOrAddQuestions(EnglishTest englishTest) {
        List<Question> savedQuestions = new ArrayList<>();
        for (Question question : englishTest.getQuestions()) {
            ensureQuestionBelongsToEnglishTest(question, englishTest);

            question.setEnglishTest(englishTest);

            if (question.getId() != null && question.equals(questionService.findById(question.getId()))) {
                question = questionService.update(question);
            } else {
                question.setId(null);
                question = questionService.create(question);
            }
            savedQuestions.add(question);
        }
        englishTest.setQuestions(savedQuestions);
    }

    private void ensureQuestionBelongsToEnglishTest(Question question, EnglishTest englishTest) {
        if (question.getEnglishTest() != null && !question.getEnglishTest().getId().equals(englishTest.getId())) {
            throw new IllegalArgumentException("EnglishTest questions do not match");
        }
    }

    @Override
    public void delete(EnglishTest englishTest) {
        Objects.requireNonNull(englishTest, "EnglishTest cannot be null");
        englishTestRepository.delete(englishTest);
    }

    @Override
    public void deleteById(Long id) {
        Objects.requireNonNull(id, "EnglishTest id cannot be null");
        englishTestRepository.deleteById(id);
    }

    @Override
    public void addQuestion(Question question) {
        questionService.create(question);
    }

    @Override
    public void deleteQuestion(Question question) {
        question.getEnglishTest().getQuestions().remove(question);

        englishTestRepository.save(question.getEnglishTest());

        questionService.delete(question);
    }

    @Override
    public EnglishTest getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<EnglishTest> englishTests = englishTestRepository.findAll(pageable);

        return englishTests.hasContent() ? englishTests.getContent().get(0) : null;
    }

    private void validateEnglishTestForCreation(EnglishTest englishTest) {
        Objects.requireNonNull(englishTest, "EnglishTest cannot be null");
        if (englishTest.getId() != null) {
            throw new IllegalArgumentException("EnglishTest ID should not be provided for creation");
        }
    }

    private void validateEnglishTestForUpdate(EnglishTest englishTest) {
        Objects.requireNonNull(englishTest, "EnglishTest cannot be null");
        Objects.requireNonNull(englishTest.getId(), "EnglishTest ID must be provided for update");
        if (!englishTestRepository.existsById(englishTest.getId())) {
            throw new IllegalArgumentException("EnglishTest with id = " + englishTest.getId() + " does not exist");
        }
    }
}