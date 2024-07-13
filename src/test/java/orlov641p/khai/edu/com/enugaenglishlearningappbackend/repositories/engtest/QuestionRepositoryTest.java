package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.engtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ActiveProfiles("test")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DataJpaTest
class QuestionRepositoryTest {
    private static final String QUESTION_TEXT = "QUESTION_TEXT";
    private static final String ANSWER = "ANSWER";
    private Question question;
    private EnglishTest englishTest;

    @Autowired
    private EnglishTestRepository englishTestRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setRule(){
        question = new Question();
        question.setQuestionText(QUESTION_TEXT);
        question.setAnswer(ANSWER);

        englishTest = new EnglishTest();
        englishTest = englishTestRepository.save(englishTest);

        question.setEnglishTest(englishTest);
    }

    @Transactional
    @Test
    void givenNewQuestion_whenSave_thenQuestionSaved() {
        Question savedRule = questionRepository.save(question);
        assertThat(entityManager.find(Question.class, savedRule.getId())).isEqualTo(question);
    }

    @Transactional
    @Test
    void givenQuestionCreated_whenUpdate_thenSuccess() {
        entityManager.persist(question);
        String newName = "NEW NAME " + question.getQuestionText();
        question.setQuestionText(newName);
        questionRepository.save(question);
        assertThat(entityManager.find(Question.class, question.getId()).getQuestionText()).isEqualTo(newName);
    }

    @Transactional
    @Test
    void givenQuestionCreated_whenFindById_thenSuccess() {
        entityManager.persist(question);
        Optional<Question> retrievedRule = questionRepository.findById(question.getId());
        assertThat(retrievedRule).contains(question);
    }

    @Transactional
    @Test
    void givenQuestionCreated_whenDelete_thenSuccess() {
        entityManager.persist(question);
        questionRepository.delete(question);
        assertThat(entityManager.find(Question.class, question.getId())).isNull();
    }

    @Transactional
    @Test
    void givenTwoCreatedQuestions_whenFindAll_thenSuccess() {
        entityManager.persist(question);

        Question question2  = new Question();
        question2.setQuestionText("QuestionText2");
        question2.setEnglishTest(englishTest);
        entityManager.persist(question2);

        List<Question> questions = questionRepository.findAll();

        assertEquals(2, questions.size());
        assertTrue(questions.contains(question));
        assertTrue(questions.contains(question2));
    }

    @Transactional
    @Test
    void findByEnglishTestId_WhenEnglishTestIdExists_ReturnsQuestions() {
        Question question2  = new Question();
        question2.setQuestionText("QuestionText2");
        question2.setEnglishTest(englishTest);

        questionRepository.save(question);
        questionRepository.save(question2);

        Long englishTestId = englishTest.getId();

        List<Question> foundQuestions = questionRepository.findByEnglishTestId(englishTestId);

        assertThat(foundQuestions).isNotEmpty();
        assertThat(foundQuestions.size()).isEqualTo(2);
        assertThat(foundQuestions).allMatch(question -> question.getEnglishTest().getId().equals(englishTestId));
    }

    @Test
    @Transactional
    void findByEnglishTestId_WhenEnglishTestIdDoesNotExist_ReturnsEmptyList() {
        Long nonExistentTestId = -1L;

        List<Question> foundQuestions = questionRepository.findByEnglishTestId(nonExistentTestId);

        assertThat(foundQuestions).isEmpty();
    }
}