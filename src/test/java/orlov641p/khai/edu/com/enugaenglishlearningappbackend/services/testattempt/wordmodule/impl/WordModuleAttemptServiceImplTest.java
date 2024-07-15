package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.wordmodule.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptPage;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptWithoutAnswers;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.testattempt.wordmodule.WordModuleAttemptRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.CustomPairService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordModuleAttemptServiceImplTest {

    @Mock
    private WordModuleAttemptRepository wordModuleAttemptRepository;

    @Mock
    private CustomPairService customPairService;

    @Mock
    private UserService userService;

    @InjectMocks
    private WordModuleAttemptServiceImpl wordModuleAttemptService;

    private final Long ID = 1L;

    private WordModuleAttempt WORD_MODULE_ATTEMPT;

    @BeforeEach
    void setUp() {
        WORD_MODULE_ATTEMPT = WordModuleAttempt.builder()
                .id(ID)
                .build();
    }

    @Test
    void whenFindAll_thenReturnsAll(){
        List<WordModuleAttempt> wordModuleAttempts = List.of(new WordModuleAttempt(), new WordModuleAttempt());

        when(wordModuleAttemptRepository.findAll()).thenReturn(wordModuleAttempts);

        assertEquals(wordModuleAttempts, wordModuleAttemptService.findAll());
    }

    @Test
    void whenFindById_idNull_thenException(){
        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.findById(null));
        String message = "WordModuleAttempt id can`t be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenFindById_attemptDoesntExist_thenException(){
        when(wordModuleAttemptRepository.findById(anyLong())).thenReturn(Optional.empty());

        var e = assertThrows(EntityNotFoundException.class, () -> wordModuleAttemptService.findById(ID));
        String message = "WordModuleAttempt with id = " + ID + " doesn't exist";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenFindById_attemptExists_thenSuccess(){
        when(wordModuleAttemptRepository.findById(anyLong())).thenReturn(Optional.of(WORD_MODULE_ATTEMPT));

        assertEquals(WORD_MODULE_ATTEMPT, wordModuleAttemptService.findById(WORD_MODULE_ATTEMPT.getId()));
    }

    @Test
    void whenCreate_null_thenException(){
        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.create(null));

        String message = "WordModuleAttempt can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenCreate_getUserNull_thenException(){
        WordModuleAttempt wordModuleAttempt = new WordModuleAttempt();

        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.create(wordModuleAttempt));

        String message = "WordModuleAttempt user can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenCreate_getEnglishTestNull_thenException(){
        WordModuleAttempt wordModuleAttempt = new WordModuleAttempt();
        wordModuleAttempt.setUser(new User());

        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.create(wordModuleAttempt));

        String message = "WordModuleAttempt english test can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenCreate_getEnglishTestQuestionsNull_thenException() {
        WordModuleAttempt wordModuleAttempt = new WordModuleAttempt();
        wordModuleAttempt.setUser(new User());
        wordModuleAttempt.setWordModule(new WordModule());

        WordModule wordModule = wordModuleAttempt.getWordModule();
        wordModule.setId(1L);

        when(customPairService.getCustomPairsByWordModuleId(1L)).thenReturn(null);

        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.create(wordModuleAttempt));

        String message = "WordModuleAttempt WordModule customPairs can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenCreate_differentAnswerSize_thenException(){
        WordModuleAttempt wordModuleAttempt = new WordModuleAttempt();
        wordModuleAttempt.setUser(new User());

        WordModule wordModule = new WordModule();
        wordModule.setId(1L);
        List<CustomPair> customPairs = new ArrayList<>();
        customPairs.add(new CustomPair(1L, "text1", "answer1", wordModule));
        customPairs.add(new CustomPair(2L, "text2", "answer2", wordModule));

        wordModule.setCustomPairs(customPairs);
        wordModuleAttempt.setWordModule(wordModule);

        when(customPairService.getCustomPairsByWordModuleId(1L)).thenReturn(customPairs);

        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.create(wordModuleAttempt));

        String message = "Wrong amount of rightAnswers and wrongAnswers";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenCreate_validData_thenSuccess100Percentage(){
        WordModuleAttempt wordModuleAttempt = new WordModuleAttempt();
        wordModuleAttempt.setRightAnswers(2);
        wordModuleAttempt.setUser(new User());

        WordModule wordModule = new WordModule();
        wordModule.setId(1L);
        List<CustomPair> customPairs = new ArrayList<>();
        customPairs.add(new CustomPair(1L, "text1", "answer1", wordModule));
        customPairs.add(new CustomPair(2L, "text2", "answer2", wordModule));

        wordModule.setCustomPairs(customPairs);
        wordModuleAttempt.setWordModule(wordModule);

        when(customPairService.getCustomPairsByWordModuleId(1L)).thenReturn(customPairs);
        when(wordModuleAttemptRepository.save(any())).thenReturn(wordModuleAttempt);

        WordModuleAttempt createdAttempt = wordModuleAttemptService.create(wordModuleAttempt);
        assertEquals(wordModuleAttempt, createdAttempt);
        assertEquals(100, createdAttempt.getSuccessPercentage());
    }

    @Test
    void whenCreate_validData_thenSuccess0Percentage(){
        WordModuleAttempt wordModuleAttempt = new WordModuleAttempt();
        wordModuleAttempt.setWrongAnswers(Map.of(1L, "ans1", 2L, "ans2"));
        wordModuleAttempt.setUser(new User());

        WordModule wordModule = new WordModule();
        wordModule.setId(1L);
        List<CustomPair> customPairs = new ArrayList<>();
        customPairs.add(new CustomPair(1L, "text1", "answer1", wordModule));
        customPairs.add(new CustomPair(2L, "text2", "answer2", wordModule));

        wordModule.setCustomPairs(customPairs);
        wordModuleAttempt.setWordModule(wordModule);

        when(customPairService.getCustomPairsByWordModuleId(1L)).thenReturn(customPairs);
        when(wordModuleAttemptRepository.save(any())).thenReturn(wordModuleAttempt);

        WordModuleAttempt createdAttempt = wordModuleAttemptService.create(wordModuleAttempt);
        assertEquals(wordModuleAttempt, createdAttempt);
        assertEquals(0, createdAttempt.getSuccessPercentage());
    }

    @Test
    void whenCreate_validData_thenSuccess50Percentage(){
        WordModuleAttempt wordModuleAttempt = new WordModuleAttempt();
        wordModuleAttempt.setRightAnswers(1);
        wordModuleAttempt.setWrongAnswers(Map.of(1L, "ans1"));
        wordModuleAttempt.setUser(new User());

        WordModule wordModule = new WordModule();
        wordModule.setId(1L);
        List<CustomPair> customPairs = new ArrayList<>();
        customPairs.add(new CustomPair(1L, "text1", "answer1", wordModule));
        customPairs.add(new CustomPair(2L, "text2", "answer2", wordModule));

        wordModule.setCustomPairs(customPairs);
        wordModuleAttempt.setWordModule(wordModule);

        when(customPairService.getCustomPairsByWordModuleId(1L)).thenReturn(customPairs);
        when(wordModuleAttemptRepository.save(any())).thenReturn(wordModuleAttempt);

        WordModuleAttempt createdAttempt = wordModuleAttemptService.create(wordModuleAttempt);
        assertEquals(wordModuleAttempt, createdAttempt);
        assertEquals(50, createdAttempt.getSuccessPercentage());
    }


    @Test
    void whenUpdate_testAttemptNull_thenException(){
        WordModuleAttempt wordModuleAttempt = null;

        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.update(wordModuleAttempt));

        String message = "WordModuleAttempt can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenUpdate_getUserNull_thenException(){
        WordModuleAttempt testAttempt = new WordModuleAttempt();

        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.update(testAttempt));

        String message = "WordModuleAttempt user can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenUpdate_getEnglishTestNull_thenException(){
        WordModuleAttempt testAttempt = new WordModuleAttempt();
        testAttempt.setUser(new User());

        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.update(testAttempt));

        String message = "WordModuleAttempt english test can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenUpdate_testAttemptGetIdNull_thenException(){
        WordModuleAttempt testAttempt = new WordModuleAttempt();
        testAttempt.setUser(new User());
        testAttempt.setWordModule(new WordModule());

        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.update(testAttempt));

        String message = "WordModuleAttempt id can`t be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenUpdate_findByIdNull_thenException(){
        WordModuleAttempt testAttempt = new WordModuleAttempt();
        testAttempt.setId(ID);
        testAttempt.setUser(new User());
        testAttempt.setWordModule(new WordModule());

        when(wordModuleAttemptRepository.findById(anyLong())).thenReturn(Optional.empty());
        var e = assertThrows(EntityNotFoundException.class, () -> wordModuleAttemptService.update(testAttempt));

        String message = "WordModuleAttempt with id = " + ID + " doesn't exist";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenUpdate_validData_thenSuccess(){
        WordModuleAttempt testAttempt = new WordModuleAttempt();
        testAttempt.setId(ID);
        testAttempt.setUser(new User());
        testAttempt.setWordModule(new WordModule());

        when(wordModuleAttemptRepository.findById(anyLong())).thenReturn(Optional.of(testAttempt));
        when(wordModuleAttemptRepository.save(any())).thenReturn(testAttempt);

        assertEquals(testAttempt, wordModuleAttemptService.update(testAttempt));
    }

    @Test
    void whenDelete_testAttemptNull_thenException(){
        WordModuleAttempt testAttempt = null;

        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.delete(testAttempt));

        String message = "WordModuleAttempt can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenDelete_getUserNull_thenException(){
        WordModuleAttempt testAttempt = new WordModuleAttempt();

        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.delete(testAttempt));

        String message = "WordModuleAttempt user can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenDelete_getEnglishTestNull_thenException(){
        WordModuleAttempt testAttempt = new WordModuleAttempt();
        testAttempt.setUser(new User());

        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.delete(testAttempt));

        String message = "WordModuleAttempt english test can't be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenDelete_validData_thenDeleted(){
        WordModuleAttempt testAttempt = new WordModuleAttempt();
        testAttempt.setUser(new User());
        testAttempt.setWordModule(new WordModule());

        assertDoesNotThrow(() -> wordModuleAttemptService.delete(testAttempt));

        verify(wordModuleAttemptRepository, times(1)).delete(any());
    }

    @Test
    void whenDeleteById_testAttemptNull_thenException(){
        var e = assertThrows(IllegalArgumentException.class, () -> wordModuleAttemptService.deleteById(null));

        String message = "WordModuleAttempt id can`t be null";

        assertEquals(message, e.getMessage());
    }

    @Test
    void whenDeleteById_getUserNull_thenException(){
        WordModuleAttempt testAttempt = new WordModuleAttempt();
        testAttempt.setId(ID);

        assertDoesNotThrow(() -> wordModuleAttemptService.deleteById(testAttempt.getId()));

        verify(wordModuleAttemptRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void whenGetFirst_validData_thenSuccess(){
        Pageable pageable = Pageable.ofSize(1).first();
        Page<WordModuleAttempt> testAttemptPage = mock(Page.class);

        WordModuleAttempt wordModuleAttempt = new WordModuleAttempt();

        when(wordModuleAttemptRepository.findAll(pageable)).thenReturn(testAttemptPage);
        when(testAttemptPage.hasContent()).thenReturn(true);
        when(testAttemptPage.getContent()).thenReturn(Collections.singletonList(wordModuleAttempt));

        WordModuleAttempt result = wordModuleAttemptService.getFirst();

        assertNotNull(result);
        assertEquals(wordModuleAttempt, result);

        verify(wordModuleAttemptRepository).findAll(pageable);
        verify(testAttemptPage).hasContent();
        verify(testAttemptPage).getContent();
    }

    @Test
    void whenFindTestAttemptsByUser_validData_thenSuccess(){
        List<WordModuleAttempt> wordModuleAttempts = List.of(WORD_MODULE_ATTEMPT);

        when(wordModuleAttemptRepository.findByUser(any())).thenReturn(wordModuleAttempts);

        List<WordModuleAttempt> list = wordModuleAttemptService.findWordModuleAttemptsByUser(new User());

        assertEquals(1, list.size());
        assertEquals(WORD_MODULE_ATTEMPT, list.get(0));
    }

    @Test
    void testFindTestAttemptsPageByUser() {
        WordModuleAttemptPage testAttemptPage = new WordModuleAttemptPage();
        testAttemptPage.setUserId(1L);
        testAttemptPage.setPageSize(10);
        testAttemptPage.setPageNumber(0);

        User user = new User();
        Page<WordModuleAttempt> wordModuleAttemptPage = mock(Page.class);

        when(userService.findById(1L)).thenReturn(user);
        when(wordModuleAttemptRepository.findByUser(eq(user), any(Pageable.class))).thenReturn(wordModuleAttemptPage);

        Page<WordModuleAttempt> result = wordModuleAttemptService.findWordModuleAttemptsPageByUser(testAttemptPage);

        assertNotNull(result);
        assertEquals(wordModuleAttemptPage, result);

        verify(userService).findById(1L);
        verify(wordModuleAttemptRepository).findByUser(eq(user), any(Pageable.class));
    }

    @Test
    void testFindLastTestAttemptsPageByUserSortedByDate() {
        WordModuleAttemptPage testAttemptPage = new WordModuleAttemptPage();
        testAttemptPage.setUserId(1L);
        testAttemptPage.setPageSize(10);
        testAttemptPage.setPageNumber(0);

        User user = new User();
        Page<WordModuleAttempt> wordModuleAttemptPage = mock(Page.class);

        when(userService.findById(1L)).thenReturn(user);
        when(wordModuleAttemptRepository.findByUser(eq(user), any(Pageable.class))).thenReturn(wordModuleAttemptPage);

        Page<WordModuleAttempt> result = wordModuleAttemptService.findLastWordModuleAttemptsPageByUserSortedByDate(testAttemptPage);

        assertNotNull(result);
        assertEquals(wordModuleAttemptPage, result);

        verify(userService).findById(1L);
        verify(wordModuleAttemptRepository).findByUser(eq(user), any(Pageable.class));
    }

    @Test
    void testFindMaximumScoreAttempt() {
        WordModuleAttemptWithoutAnswers wordModuleAttemptWithoutAnswers = new WordModuleAttemptWithoutAnswers();
        wordModuleAttemptWithoutAnswers.setUserId(1L);
        wordModuleAttemptWithoutAnswers.setWordModuleId(2L);

        User user = new User();
        List<WordModuleAttempt> testAttempts = Collections.singletonList(new WordModuleAttempt());

        when(userService.findById(1L)).thenReturn(user);
        when(wordModuleAttemptRepository.findTopByUserAndWordModuleOrderBySuccessPercentageDesc(eq(user), eq(2L))).thenReturn(testAttempts);

        WordModuleAttempt result = wordModuleAttemptService.findMaximumScoreWordModuleAttempt(wordModuleAttemptWithoutAnswers);

        assertNotNull(result);
        assertEquals(testAttempts.get(0), result);

        verify(userService).findById(1L);
        verify(wordModuleAttemptRepository).findTopByUserAndWordModuleOrderBySuccessPercentageDesc(eq(user), eq(2L));
    }

    @Test
    void testFindLastAttemptScore() {
        WordModuleAttemptWithoutAnswers wordModuleAttemptWithoutAnswers = new WordModuleAttemptWithoutAnswers();
        wordModuleAttemptWithoutAnswers.setUserId(1L);
        wordModuleAttemptWithoutAnswers.setWordModuleId(2L);

        User user = new User();
        List<WordModuleAttempt> testAttempts = Collections.singletonList(new WordModuleAttempt());

        when(userService.findById(1L)).thenReturn(user);
        when(wordModuleAttemptRepository.findNewestByUserAndWordModuleOrderByAttemptDateDesc(eq(user), eq(2L))).thenReturn(testAttempts);

        WordModuleAttempt result = wordModuleAttemptService.findLastWordModuleAttemptScore(wordModuleAttemptWithoutAnswers);

        assertNotNull(result);
        assertEquals(testAttempts.get(0), result);

        verify(userService).findById(1L);
        verify(wordModuleAttemptRepository).findNewestByUserAndWordModuleOrderByAttemptDateDesc(eq(user), eq(2L));
    }

    @Test
    void when_findLastPublicWordModuleAttemptsPageByUserSortedByDate_success(){
        WordModuleAttemptPage testAttemptPage = new WordModuleAttemptPage();
        testAttemptPage.setUserId(1L);
        testAttemptPage.setPageSize(10);
        testAttemptPage.setPageNumber(0);
        Page<WordModuleAttempt> wordModuleAttemptPage = mock(Page.class);

        when(userService.findById(anyLong())).thenReturn(new User());
        when(wordModuleAttemptRepository.findNewestPublicByUserOrderByAttemptDateDesc(any(), any()))
                .thenReturn(wordModuleAttemptPage);

        assertEquals(wordModuleAttemptPage,
                wordModuleAttemptService.findLastPublicWordModuleAttemptsPageByUserSortedByDate(testAttemptPage));
    }
}