package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.engtest.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request.TestAttemptPage;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request.TestAttemptWithoutAnswers;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.engtest.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.testattempt.engtest.TestAttemptRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.QuestionService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestAttemptServiceImplTest {

    @Mock
    private TestAttemptRepository testAttemptRepository;

    @Mock
    private QuestionService questionService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TestAttemptServiceImpl  testAttemptService;

    private Long ID = 1L;

    private TestAttempt TEST_ATTEMPT;

    @BeforeEach
    void setUp() {
        TEST_ATTEMPT = TestAttempt.builder()
                .id(ID)
                .build();
    }

    @Test
    void whenFindAll_thenReturnsAll(){
        List<TestAttempt> testAttempts = List.of(new TestAttempt(), new TestAttempt());

        when(testAttemptRepository.findAll()).thenReturn(testAttempts);

        assertEquals(testAttempts, testAttemptService.findAll());
    }

    @Test
    void whenFindById_idNull_thenException(){
        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.findById(null));
        String message = "TestAttempt id can`t be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenFindById_attemptDoesntExist_thenException(){
        when(testAttemptRepository.findById(anyLong())).thenReturn(Optional.empty());

        var e = assertThrows(EntityNotFoundException.class, () -> testAttemptService.findById(ID));
        String message = "TestAttempt with id = " + ID + " doesn't exist";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenFindById_attemptExists_thenSuccess(){
        when(testAttemptRepository.findById(anyLong())).thenReturn(Optional.of(TEST_ATTEMPT));

        assertEquals(TEST_ATTEMPT, testAttemptService.findById(TEST_ATTEMPT.getId()));
    }

    @Test
    void whenCreate_null_thenException(){
        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.create(null));

        String message = "TestAttempt can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenCreate_getUserNull_thenException(){
        TestAttempt testAttempt = new TestAttempt();

        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.create(testAttempt));

        String message = "TestAttempt user can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenCreate_getEnglishTestNull_thenException(){
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setUser(new User());

        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.create(testAttempt));

        String message = "TestAttempt english test can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenCreate_getEnglishTestQuestionsNull_thenException() {
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setUser(new User());
        testAttempt.setEnglishTest(new EnglishTest());

        EnglishTest englishTest = testAttempt.getEnglishTest();
        englishTest.setId(1L);

        when(questionService.getQuestionsByEnglishTestId(1L)).thenReturn(null);

        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.create(testAttempt));

        String message = "TestAttempt EnglishTest questions can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenCreate_differentAnswerSize_thenException(){
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setUser(new User());

        EnglishTest englishTest = new EnglishTest();
        englishTest.setId(1L);
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1L, "text1", "answer1", englishTest));
        questions.add(new Question(2L, "text2", "answer2", englishTest));

        englishTest.setQuestions(questions);
        testAttempt.setEnglishTest(englishTest);

        when(questionService.getQuestionsByEnglishTestId(1L)).thenReturn(questions);

        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.create(testAttempt));

        String message = "Wrong amount of rightAnswers and wrongAnswers";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenCreate_validData_thenSuccess100Percentage(){
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setRightAnswers(2);
        testAttempt.setUser(new User());

        EnglishTest englishTest = new EnglishTest();
        englishTest.setId(1L);
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1L, "text1", "answer1", englishTest));
        questions.add(new Question(2L, "text2", "answer2", englishTest));

        englishTest.setQuestions(questions);
        testAttempt.setEnglishTest(englishTest);

        when(questionService.getQuestionsByEnglishTestId(1L)).thenReturn(questions);
        when(testAttemptRepository.save(any())).thenReturn(testAttempt);

        TestAttempt createdAttempt = testAttemptService.create(testAttempt);
        assertEquals(testAttempt, createdAttempt);
        assertEquals(100, createdAttempt.getSuccessPercentage());
    }

    @Test
    void whenCreate_validData_thenSuccess0Percentage(){
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setWrongAnswers(Map.of(1L, "ans1", 2L, "ans2"));
        testAttempt.setUser(new User());

        EnglishTest englishTest = new EnglishTest();
        englishTest.setId(1L);
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1L, "text1", "answer1", englishTest));
        questions.add(new Question(2L, "text2", "answer2", englishTest));

        englishTest.setQuestions(questions);
        testAttempt.setEnglishTest(englishTest);

        when(questionService.getQuestionsByEnglishTestId(1L)).thenReturn(questions);
        when(testAttemptRepository.save(any())).thenReturn(testAttempt);

        TestAttempt createdAttempt = testAttemptService.create(testAttempt);
        assertEquals(testAttempt, createdAttempt);
        assertEquals(0, createdAttempt.getSuccessPercentage());
    }

    @Test
    void whenCreate_validData_thenSuccess50Percentage(){
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setRightAnswers(1);
        testAttempt.setWrongAnswers(Map.of(1L, "ans1"));
        testAttempt.setUser(new User());

        EnglishTest englishTest = new EnglishTest();
        englishTest.setId(1L);
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1L, "text1", "answer1", englishTest));
        questions.add(new Question(2L, "text2", "answer2", englishTest));

        englishTest.setQuestions(questions);
        testAttempt.setEnglishTest(englishTest);

        when(questionService.getQuestionsByEnglishTestId(1L)).thenReturn(questions);
        when(testAttemptRepository.save(any())).thenReturn(testAttempt);

        TestAttempt createdAttempt = testAttemptService.create(testAttempt);
        assertEquals(testAttempt, createdAttempt);
        assertEquals(50, createdAttempt.getSuccessPercentage());
    }


    @Test
    void whenUpdate_testAttemptNull_thenException(){
        TestAttempt testAttempt = null;

        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.update(testAttempt));

        String message = "TestAttempt can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenUpdate_getUserNull_thenException(){
        TestAttempt testAttempt = new TestAttempt();

        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.update(testAttempt));

        String message = "TestAttempt user can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenUpdate_getEnglishTestNull_thenException(){
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setUser(new User());

        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.update(testAttempt));

        String message = "TestAttempt english test can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenUpdate_testAttemptGetIdNull_thenException(){
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setUser(new User());
        testAttempt.setEnglishTest(new EnglishTest());

        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.update(testAttempt));

        String message = "TestAttempt id can`t be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenUpdate_findByIdNull_thenException(){
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setId(ID);
        testAttempt.setUser(new User());
        testAttempt.setEnglishTest(new EnglishTest());

        when(testAttemptRepository.findById(anyLong())).thenReturn(Optional.empty());
        var e = assertThrows(EntityNotFoundException.class, () -> testAttemptService.update(testAttempt));

        String message = "TestAttempt with id = " + ID + " doesn't exist";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenUpdate_validData_thenSuccess(){
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setId(ID);
        testAttempt.setUser(new User());
        testAttempt.setEnglishTest(new EnglishTest());

        when(testAttemptRepository.findById(anyLong())).thenReturn(Optional.of(testAttempt));
        when(testAttemptRepository.save(any())).thenReturn(testAttempt);

        assertEquals(testAttempt, testAttemptService.update(testAttempt));
    }

    @Test
    void whenDelete_testAttemptNull_thenException(){
        TestAttempt testAttempt = null;

        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.delete(testAttempt));

        String message = "TestAttempt can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenDelete_getUserNull_thenException(){
        TestAttempt testAttempt = new TestAttempt();

        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.delete(testAttempt));

        String message = "TestAttempt user can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenDelete_getEnglishTestNull_thenException(){
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setUser(new User());

        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.delete(testAttempt));

        String message = "TestAttempt english test can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenDelete_validData_thenDeleted(){
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setUser(new User());
        testAttempt.setEnglishTest(new EnglishTest());

        assertDoesNotThrow(() -> testAttemptService.delete(testAttempt));

        verify(testAttemptRepository, times(1)).delete(any());
    }

    @Test
    void whenDeleteById_testAttemptNull_thenException(){
        var e = assertThrows(IllegalArgumentException.class, () -> testAttemptService.deleteById(null));

        String message = "TestAttempt id can`t be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenDeleteById_getUserNull_thenException(){
        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setId(ID);

        assertDoesNotThrow(() -> testAttemptService.deleteById(testAttempt.getId()));

        verify(testAttemptRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void whenGetFirst_validData_thenSuccess(){
        Pageable pageable = Pageable.ofSize(1).first();
        Page<TestAttempt> testAttemptPage = mock(Page.class);

        TestAttempt testAttempt = new TestAttempt();

        when(testAttemptRepository.findAll(pageable)).thenReturn(testAttemptPage);
        when(testAttemptPage.hasContent()).thenReturn(true);
        when(testAttemptPage.getContent()).thenReturn(Collections.singletonList(testAttempt));

        TestAttempt result = testAttemptService.getFirst();

        assertNotNull(result);
        assertEquals(testAttempt, result);

        verify(testAttemptRepository).findAll(pageable);
        verify(testAttemptPage).hasContent();
        verify(testAttemptPage).getContent();
    }

    @Test
    void whenFindTestAttemptsByUser_validData_thenSuccess(){
        List<TestAttempt> testAttempts = List.of(TEST_ATTEMPT);

        when(testAttemptRepository.findByUser(any())).thenReturn(testAttempts);

        List<TestAttempt> list = testAttemptService.findTestAttemptsByUser(new User());

        assertEquals(1, list.size());
        assertEquals(TEST_ATTEMPT, list.get(0));
    }

    @Test
    void testFindTestAttemptsPageByUser() {
        TestAttemptPage testAttemptPage = new TestAttemptPage();
        testAttemptPage.setUserId(1L);
        testAttemptPage.setPageSize(10);
        testAttemptPage.setPageNumber(0);

        User user = new User();
        Page<TestAttempt> testAttemptsPage = mock(Page.class);

        when(userService.findById(1L)).thenReturn(user);
        when(testAttemptRepository.findByUser(eq(user), any(Pageable.class))).thenReturn(testAttemptsPage);

        Page<TestAttempt> result = testAttemptService.findTestAttemptsPageByUser(testAttemptPage);

        assertNotNull(result);
        assertEquals(testAttemptsPage, result);

        verify(userService).findById(1L);
        verify(testAttemptRepository).findByUser(eq(user), any(Pageable.class));
    }

    @Test
    void testFindLastTestAttemptsPageByUserSortedByDate() {
        TestAttemptPage testAttemptPage = new TestAttemptPage();
        testAttemptPage.setUserId(1L);
        testAttemptPage.setPageSize(10);
        testAttemptPage.setPageNumber(0);

        User user = new User();
        Page<TestAttempt> testAttemptsPage = mock(Page.class);

        when(userService.findById(1L)).thenReturn(user);
        when(testAttemptRepository.findByUser(eq(user), any(Pageable.class))).thenReturn(testAttemptsPage);

        Page<TestAttempt> result = testAttemptService.findLastTestAttemptsPageByUserSortedByDate(testAttemptPage);

        assertNotNull(result);
        assertEquals(testAttemptsPage, result);

        verify(userService).findById(1L);
        verify(testAttemptRepository).findByUser(eq(user), any(Pageable.class));
    }

    @Test
    void testFindMaximumScoreAttempt() {
        TestAttemptWithoutAnswers testAttempt = new TestAttemptWithoutAnswers();
        testAttempt.setUserId(1L);
        testAttempt.setEnglishTestId(2L);

        User user = new User();
        List<TestAttempt> testAttempts = Collections.singletonList(new TestAttempt());

        when(userService.findById(1L)).thenReturn(user);
        when(testAttemptRepository.findTopByUserAndEnglishTestOrderBySuccessPercentageDesc(eq(user), eq(2L))).thenReturn(testAttempts);

        TestAttempt result = testAttemptService.findMaximumScoreAttempt(testAttempt);

        assertNotNull(result);
        assertEquals(testAttempts.get(0), result);

        verify(userService).findById(1L);
        verify(testAttemptRepository).findTopByUserAndEnglishTestOrderBySuccessPercentageDesc(eq(user), eq(2L));
    }

    @Test
    void testFindLastAttemptScore() {
        TestAttemptWithoutAnswers testAttempt = new TestAttemptWithoutAnswers();
        testAttempt.setUserId(1L);
        testAttempt.setEnglishTestId(2L);

        User user = new User();
        List<TestAttempt> testAttempts = Collections.singletonList(new TestAttempt());

        when(userService.findById(1L)).thenReturn(user);
        when(testAttemptRepository.findNewestByUserAndEnglishTestOrderByAttemptDateDesc(eq(user), eq(2L))).thenReturn(testAttempts);

        TestAttempt result = testAttemptService.findLastAttemptScore(testAttempt);

        assertNotNull(result);
        assertEquals(testAttempts.get(0), result);

        verify(userService).findById(1L);
        verify(testAttemptRepository).findNewestByUserAndEnglishTestOrderByAttemptDateDesc(eq(user), eq(2L));
    }
}