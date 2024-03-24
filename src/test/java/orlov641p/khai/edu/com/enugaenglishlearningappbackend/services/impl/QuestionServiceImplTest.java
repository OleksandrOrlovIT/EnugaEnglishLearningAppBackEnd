package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    private static final String QUESTION_TEXT = "Some text";
    private static final String ANSWER = "Answer";
    private static final long ID = 1L;

    private Question returnQuestion;

    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        returnQuestion = Question
                .builder()
                .id(ID)
                .questionText(QUESTION_TEXT)
                .answer(ANSWER)
                .build();
    }

    @Test
    void findAll() {
        List<Question> questions = new ArrayList<>();
        questions.add(Question.builder().id(2L).build());
        questions.add(Question.builder().id(3L).build());

        when(questionRepository.findAll()).thenReturn(questions);

        List<Question> returnedQuestions = questionRepository.findAll();

        assertNotNull(returnedQuestions);

        assertEquals(questions, returnedQuestions);
    }

    @Test
    void findById() {
        when(questionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnQuestion));

        Question question = questionService.findById(ID);

        assertNotNull(question);
    }

    @Test
    void findByIdNotFound() {
        when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());

        Question question = questionService.findById(ID);

        assertNull(question);
    }

    @Test
    void save() {
        when(questionRepository.save(any())).thenReturn(returnQuestion);

        Question question = questionService.save(Question.builder().id(1L).build());

        assertNotNull(question);
        verify(questionRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        questionService.delete(returnQuestion);

        verify(questionRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        questionService.delete(returnQuestion);

        verify(questionRepository, times(1)).delete(any());
    }
}