package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.EnglishTestRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.QuestionRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.QuestionService;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final EnglishTestRepository englishTestRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, EnglishTestRepository englishTestRepository) {
        this.questionRepository = Objects.requireNonNull(questionRepository, "QuestionRepository cannot be null");
        this.englishTestRepository = Objects.requireNonNull(englishTestRepository, "EnglishTestRepository cannot be null");
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question findById(Long id) {
        Objects.requireNonNull(id, "Question id cannot be null");
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question with id = " + id + " doesn't exist"));
    }

    @Override
    public Question create(Question question) {
        validateQuestionForCreation(question);
        validateEnglishTestExistence(question.getEnglishTest());
        return questionRepository.save(question);
    }

    @Override
    public Question update(Question question) {
        validateQuestionForUpdate(question);
        validateEnglishTestExistence(question.getEnglishTest());
        return questionRepository.save(question);
    }

    @Override
    public void delete(Question question) {
        Objects.requireNonNull(question, "Question cannot be null");
        questionRepository.delete(question);
    }

    @Override
    public void deleteById(Long id) {
        Objects.requireNonNull(id, "Question id cannot be null");
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> getQuestionsByEnglishTestId(Long id) {
        Objects.requireNonNull(id, "English Test id cannot be null");
        return questionRepository.findByEnglishTestId(id);
    }

    @Override
    public Question getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<Question> questions = questionRepository.findAll(pageable);

        return questions.hasContent() ? questions.getContent().get(0) : null;
    }

    private void validateQuestionForCreation(Question question) {
        Objects.requireNonNull(question, "Question cannot be null");
        if (question.getId() != null) {
            throw new IllegalArgumentException("Question ID should not be provided for creation");
        }
        validateEnglishTestAssociation(question.getEnglishTest());
    }

    private void validateQuestionForUpdate(Question question) {
        Objects.requireNonNull(question, "Question cannot be null");
        Objects.requireNonNull(question.getId(), "Question ID must be provided for update");
        if (!questionRepository.existsById(question.getId())) {
            throw new IllegalArgumentException("Question with id = " + question.getId() + " does not exist");
        }
        validateEnglishTestAssociation(question.getEnglishTest());
        validateEnglishTestConsistency(question);
    }

    private void validateEnglishTestAssociation(EnglishTest englishTest) {
        if (englishTest == null) {
            throw new IllegalArgumentException("Question must have an associated English test");
        }
    }

    private void validateEnglishTestExistence(EnglishTest englishTest) {
        Objects.requireNonNull(englishTest, "English Test cannot be null");
        englishTestRepository.findById(englishTest.getId())
                .orElseThrow(() -> new IllegalArgumentException("English Test with id = " + englishTest.getId() + " doesn't exist"));
    }

    private void validateEnglishTestConsistency(Question question) {
        Question foundQuestion = findById(question.getId());
        if (!foundQuestion.getEnglishTest().equals(question.getEnglishTest())) {
            throw new IllegalArgumentException("Question found by id = " + foundQuestion +
                    " have different EnglishTest = " + foundQuestion.getEnglishTest() +
                    "\nWith passed question = " + question + " and its EnglishTest = " + question.getEnglishTest());
        }
    }
}