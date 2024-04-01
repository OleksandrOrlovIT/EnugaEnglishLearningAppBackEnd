package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.EnglishTestRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.QuestionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class QuestionServiceImplTest {

    private static final String QUESTION_TEXT = "Some text";
    private static final String ANSWER = "Answer";

    private static Question validQuestion;
    private static EnglishTest englishTest;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private EnglishTestRepository englishTestRepository;

    @BeforeAll
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
    @Order(1)
    void create_NullEnglishTest(){
        assertThrows(NullPointerException.class, () -> questionService.create(null));
    }

    @Test
    @Order(2)
    void create_EnglishTestWithExistingId(){
        assertThrows(IllegalArgumentException.class, () -> questionService.create(Question.builder().id(1L).build()));
    }

    @Test
    @Order(3)
    void create_ValidQuestion(){
        Question question = questionService.create(validQuestion);

        assertEquals(question, validQuestion);
        validQuestion = question;
    }

    @Test
    @Order(4)
    void findById_InvalidId(){
        assertThrows(NullPointerException.class, () -> questionService.findById(null));
        assertThrows(IllegalArgumentException.class, () -> questionService.findById(validQuestion.getId() + 100));
    }

    @Test
    @Order(5)
    void findById_ValidId(){
        Question question = questionService.findById(validQuestion.getId());

        assertEquals(question, validQuestion);
    }

    @Test
    @Order(6)
    void update_Null(){
        assertThrows(NullPointerException.class, () -> questionService.update(null));
    }

    @Test
    @Order(7)
    void update_QuestionWithNullId(){
        assertThrows(NullPointerException.class, () -> questionService.update(Question.builder().id(null).build()));
    }

    @Test
    @Order(8)
    void update_QuestionWithWrongId(){
        Question question = Question.builder().id(validQuestion.getId() + 100).build();

        assertThrows(IllegalArgumentException.class, () -> questionService.update(question));
    }

    @Test
    @Order(9)
    void update_QuestionWithNullEnglishTest(){
        Question question = Question.builder().id(validQuestion.getId()).englishTest(null).build();

        assertThrows(IllegalArgumentException.class, () -> questionService.update(question));
    }

    @Test
    @Order(10)
    void update_QuestionWithDifferentEnglishTest(){
        Question question = Question.builder().id(validQuestion.getId()).englishTest(new EnglishTest()).build();

        assertThrows(IllegalArgumentException.class, () -> questionService.update(question));
    }

    @Test
    @Order(11)
    void getQuestionsByEnglishTestId_NullID(){
        assertThrows(NullPointerException.class, () -> questionService.getQuestionsByEnglishTestId(null));
    }

    @Test
    @Order(12)
    void getQuestionsByEnglishTestId_validQuestions(){
        assertEquals(1, questionService.getQuestionsByEnglishTestId(englishTest.getId()).size());
    }

    @Test
    @Order(13)
    void update_validQuestion(){
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
    @Order(14)
    void findAll(){
        List<Question> questions = new ArrayList<>(List.of(validQuestion));
        assertEquals(questions, questionService.findAll());

        Question question = Question.builder().englishTest(englishTest).build();
        questions.add(question);
        questionService.create(question);

        assertEquals(questions, questionService.findAll());
    }

    @Test
    @Order(15)
    void delete_null(){
        assertThrows(NullPointerException.class, () -> questionService.delete(null));
    }

    @Test
    @Order(16)
    void delete_nonExistingQuestion(){
        assertDoesNotThrow(() -> questionService.delete(Question.builder().id(1000L).build()));
    }


    @Test
    @Order(17)
    void delete_ExistingQuestion(){
        Long savedId = validQuestion.getId();
        assertDoesNotThrow(() -> questionService.delete(validQuestion));

        assertThrows(IllegalArgumentException.class, () -> questionService.findById(savedId));
    }

    @Test
    @Order(18)
    void deleteById_null(){
        assertThrows(NullPointerException.class, () -> questionService.deleteById(null));
    }

    @Test
    @Order(19)
    void deleteById_nonExistingQuestionId(){
        questionService.deleteById(100000L);

        assertDoesNotThrow(() -> questionService.findById(validQuestion.getId() + 1));
    }

    @Test
    @Order(20)
    void deleteById_validQuestion(){
        questionService.deleteById(validQuestion.getId() + 1);

        assertThrows(IllegalArgumentException.class, () -> questionService.findById(validQuestion.getId() + 1));
    }

    @Test
    @Order(21)
    void create_EnglishTestWithNonExistedId(){
        Question question = Question.builder()
                        .englishTest(EnglishTest.builder().id(123123L).build())
                                .build();

        assertThrows(IllegalArgumentException.class, () -> questionService.create(question));
    }

    @AfterAll
    void deleteEnglishTest(){
        englishTestRepository.delete(englishTest);
    }
}