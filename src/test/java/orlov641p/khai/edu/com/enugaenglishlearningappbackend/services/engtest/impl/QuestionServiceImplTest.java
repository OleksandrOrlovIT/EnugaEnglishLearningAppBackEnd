package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.engtest.EnglishTestRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.QuestionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class QuestionServiceImplTest {

    private static final String QUESTION_TEXT = "Some text";
    private static final String ANSWER = "Answer";

    private static Question validQuestion;
    private static EnglishTest englishTest;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private EnglishTestRepository englishTestRepository;

    @BeforeEach
    void setUp() {
         englishTest = EnglishTest.builder()
                 .build();

         validQuestion = Question.builder()
                 .questionText(QUESTION_TEXT)
                 .answer(ANSWER)
                 .englishTest(englishTest)
                 .build();

         englishTest = englishTestRepository.save(englishTest);
    }

    @Test
    @Transactional
    void create_NullEnglishTest(){
        assertThrows(NullPointerException.class, () -> questionService.create(null));
    }

    @Test
    @Transactional
    void create_EnglishTestWithExistingId(){
        assertThrows(IllegalArgumentException.class, () -> questionService.create(Question.builder().id(1L).build()));
    }

    @Test
    @Transactional
    void create_ValidQuestion(){
        Question question = questionService.create(validQuestion);

        assertEquals(question, validQuestion);
        validQuestion = question;
    }

    @Test
    @Transactional
    void findById_InvalidId(){
        validQuestion = questionService.create(validQuestion);

        assertThrows(NullPointerException.class, () -> questionService.findById(null));
        assertThrows(EntityNotFoundException.class, () -> questionService.findById(validQuestion.getId() + 100));
    }

    @Test
    @Transactional
    void findById_ValidId(){
        validQuestion = questionService.create(validQuestion);

        Question question = questionService.findById(validQuestion.getId());

        assertEquals(question, validQuestion);
    }

    @Test
    @Transactional
    void update_Null(){
        assertThrows(NullPointerException.class, () -> questionService.update(null));
    }

    @Test
    @Transactional
    void update_QuestionWithNullId(){
        assertThrows(NullPointerException.class, () -> questionService.update(Question.builder().id(null).build()));
    }

    @Test
    @Transactional
    void update_QuestionWithWrongId(){
        validQuestion = questionService.create(validQuestion);

        Question question = Question.builder().id(validQuestion.getId() + 100).build();

        assertThrows(IllegalArgumentException.class, () -> questionService.update(question));
    }

    @Test
    @Transactional
    void update_QuestionWithNullEnglishTest(){
        validQuestion = questionService.create(validQuestion);

        Question question = Question.builder().id(validQuestion.getId()).englishTest(null).build();

        assertThrows(IllegalArgumentException.class, () -> questionService.update(question));
    }

    @Test
    @Transactional
    void update_QuestionWithDifferentEnglishTest(){
        validQuestion = questionService.create(validQuestion);

        Question question = Question.builder().id(validQuestion.getId()).englishTest(new EnglishTest()).build();

        assertThrows(IllegalArgumentException.class, () -> questionService.update(question));
    }

    @Test
    @Transactional
    void getQuestionsByEnglishTestId_NullID(){
        assertThrows(NullPointerException.class, () -> questionService.getQuestionsByEnglishTestId(null));
    }

    @Test
    @Transactional
    void getQuestionsByEnglishTestId_validQuestions(){
        validQuestion = questionService.create(validQuestion);

        assertEquals(1, questionService.getQuestionsByEnglishTestId(englishTest.getId()).size());
    }

    @Test
    @Transactional
    void update_validQuestion(){
        validQuestion = questionService.create(validQuestion);

        String updatedQuestionText = QUESTION_TEXT + "Update";
        String updatedAnswer = ANSWER + "Update";

        Question question = Question.builder()
                .id(validQuestion.getId())
                .questionText(updatedQuestionText)
                .answer(updatedAnswer)
                .englishTest(validQuestion.getEnglishTest())
                .build();

        validQuestion = questionService.update(question);

        assertEquals(validQuestion, question);
    }

    @Test
    @Transactional
    void findAll(){
        validQuestion = questionService.create(validQuestion);

        List<Question> questions = new ArrayList<>(List.of(validQuestion));
        assertEquals(questions, questionService.findAll());

        Question question = Question.builder().englishTest(englishTest).build();
        questions.add(question);
        questionService.create(question);

        assertEquals(questions, questionService.findAll());
    }

    @Test
    @Transactional
    void delete_null(){
        assertThrows(NullPointerException.class, () -> questionService.delete(null));
    }

    @Test
    @Transactional
    void delete_nonExistingQuestion(){
        assertDoesNotThrow(() -> questionService.delete(Question.builder().id(1000L).build()));
    }


    @Test
    @Transactional
    void delete_ExistingQuestion(){
        validQuestion = questionService.create(validQuestion);

        Long savedId = validQuestion.getId();
        assertDoesNotThrow(() -> questionService.delete(validQuestion));

        assertThrows(EntityNotFoundException.class, () -> questionService.findById(savedId));
    }

    @Test
    @Transactional
    void deleteById_null(){
        assertThrows(NullPointerException.class, () -> questionService.deleteById(null));
    }

    @Test
    @Transactional
    void deleteById_nonExistingQuestionId(){
        assertDoesNotThrow(() -> questionService.deleteById(100000L));
    }

    @Test
    @Transactional
    void deleteById_validQuestion(){
        validQuestion = questionService.create(validQuestion);

        questionService.deleteById(validQuestion.getId() + 1);

        assertThrows(EntityNotFoundException.class, () -> questionService.findById(validQuestion.getId() + 1));
    }

    @Test
    @Transactional
    void create_EnglishTestWithNonExistedId(){
        Question question = Question.builder()
                        .englishTest(EnglishTest.builder().id(123123L).build())
                                .build();

        assertThrows(IllegalArgumentException.class, () -> questionService.create(question));
    }
}