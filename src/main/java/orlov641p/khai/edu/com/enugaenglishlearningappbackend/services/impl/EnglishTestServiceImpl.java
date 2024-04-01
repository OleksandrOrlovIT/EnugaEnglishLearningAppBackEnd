package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.EnglishTestRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.EnglishTestService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.QuestionService;

import java.util.List;
import java.util.Objects;

@Service
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
        return englishTestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("EnglishTest with id = " + id + " doesn't exist"));
    }

    @Override
    public EnglishTest create(EnglishTest englishTest) {
        validateEnglishTestForCreation(englishTest);
        return englishTestRepository.save(englishTest);
    }

    @Override
    public EnglishTest update(EnglishTest englishTest) {
        validateEnglishTestForUpdate(englishTest);
        return englishTestRepository.save(englishTest);
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

    //TODO add more tests with nulls for add and delete
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