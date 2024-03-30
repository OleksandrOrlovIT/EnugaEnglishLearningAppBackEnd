package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.EnglishTestRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.QuestionService;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnglishTestServiceImplTest {
    private static final String TEST_NAME = "Test Name";

    private static  final Long ID = 1L;

    private EnglishTest returnEnglishTest;

    @Mock
    EnglishTestRepository englishTestRepository;

    @Mock
    QuestionService questionService;

    @InjectMocks
    EnglishTestServiceImpl englishTestService;

    @BeforeEach
    void setUp() {
        returnEnglishTest = EnglishTest
                .builder()
                .id(ID)
                .testName(TEST_NAME)
                .build();
    }

    @Test
    void findAll() {
        List<EnglishTest> englishTests = new ArrayList<>();
        englishTests.add(returnEnglishTest);
        englishTests.add(EnglishTest.builder().id(2L).build());

        List<Question> questions1 = List.of(Question.builder().build());
        List<Question> questions2 = List.of(Question.builder().build(), Question.builder().build());

        when(englishTestRepository.findAll()).thenReturn(englishTests);
        when(questionService.getQuestionsByEnglishTestId(ID)).thenReturn(questions1);
        when(questionService.getQuestionsByEnglishTestId(2L)).thenReturn(questions2);

        List<EnglishTest> foundedEnglishTests = englishTestService.findAll();

        assertEquals(foundedEnglishTests.size(), englishTests.size());
        assertEquals(foundedEnglishTests.get(0).getQuestions(), questions1);
        assertEquals(foundedEnglishTests.get(1).getQuestions(), questions2);
    }

    @Test
    void findById(){
        when(englishTestRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnEnglishTest));
        when(questionService.getQuestionsByEnglishTestId(anyLong())).thenReturn(List.of(Question.builder().build()));

        EnglishTest englishTest = englishTestService.findById(ID);

        assertNotNull(englishTest);
        assertEquals(englishTest.getQuestions().size(), 1);
    }

    @Test
    void findByIdNotFound(){
        when(englishTestRepository.findById(anyLong())).thenReturn(Optional.empty());

        EnglishTest englishTest = englishTestService.findById(ID);

        assertNull(englishTest);
    }

    @Test
    void save() {
        when(englishTestRepository.save(any())).thenReturn(returnEnglishTest);

        EnglishTest englishTest = englishTestService.save(returnEnglishTest);

        assertEquals(englishTest, returnEnglishTest);
    }

    @Test
    void delete() {
        List<Question> questions = List.of(Question.builder().build(), Question.builder().build());

        when(englishTestRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnEnglishTest));
        when(questionService.getQuestionsByEnglishTestId(anyLong())).thenReturn(questions);

        englishTestService.delete(returnEnglishTest);

        verify(questionService, times(2)).delete(any());
        verify(questionService, times(1)).getQuestionsByEnglishTestId(anyLong());
        verify(englishTestRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteById() {
        List<Question> questions = List.of(Question.builder().build(), Question.builder().build());

        when(englishTestRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnEnglishTest));
        when(questionService.getQuestionsByEnglishTestId(anyLong())).thenReturn(questions);

        englishTestService.deleteById(returnEnglishTest.getId());

        verify(questionService, times(2)).delete(any());
        verify(questionService, times(1)).getQuestionsByEnglishTestId(anyLong());
        verify(englishTestRepository, times(1)).findById(anyLong());
    }
}