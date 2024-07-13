package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.engtest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.QuestionService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    private static final long questionId = 1L;

    @Mock
    QuestionService questionService;

    @InjectMocks
    QuestionController questionController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
    }

    @Test
    void retrieveQuestions() throws Exception {
        when(questionService.findAll())
                .thenReturn(Arrays.asList(Question.builder().build(), Question.builder().build()));

        mockMvc.perform(get("/v1/questions")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveRuleById() throws Exception {
        String questionText = "Some text", answer = "answer";
        Question expectedQuestion = Question.builder()
                .id(questionId)
                .questionText(questionText)
                .answer(answer)
                .build();

        when(questionService.findById(questionId)).thenReturn(expectedQuestion);

        MvcResult result = mockMvc.perform(get("/v1/question/{id}", questionId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        EntityModel<Question> responseModel = new ObjectMapper().readValue(responseBody, new TypeReference<>() {});

        assertEquals(expectedQuestion, responseModel.getContent());
    }

    @Test
    void deleteQuestion() throws Exception{
        mockMvc.perform(delete("/v1/question/{id}", 1)).andExpect(status().is(204));
    }

    @Test
    void updateQuestion() throws Exception {
        Question originalQuestion = Question.builder().questionText("some text").answer("answer").build();
        Question updateQuestion = Question.builder()
                .id(questionId).questionText("updated text").answer("updated answer")
                .build();

        when(questionService.findById(anyLong())).thenReturn(originalQuestion);
        when(questionService.update(any())).thenReturn(updateQuestion);

        MvcResult result = mockMvc.perform(put("/v1/question/{id}", questionId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(updateQuestion)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Question responseQuestion = new ObjectMapper().readValue(responseBody, Question.class);

        assertNotNull(responseQuestion);
        assertEquals(updateQuestion, responseQuestion);
    }

    @Test
    void createQuestion() throws Exception {
        Question inputQuestion = Question.builder().questionText("some text").build();
        Question savedQuestion = Question.builder().id(questionId).questionText("some text").build();

        when(questionService.create(any())).thenReturn(savedQuestion);

        MvcResult result = mockMvc.perform(post("/v1/question")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(inputQuestion)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");

        assertNotNull(locationHeader);
        assertEquals("http://localhost/v1/question/1", locationHeader);
    }

    @Test
    void retrieveQuestionsByEnglishTestId() throws Exception {
        List<Question> questions = List.of(new Question(), new Question());
        when(questionService.getQuestionsByEnglishTestId(anyLong())).thenReturn(questions);

        mockMvc.perform(get("/v1/questions/english-test/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void updateQuestion_findByIdNull() throws Exception {
        Question updateQuestion = Question.builder()
                .id(questionId).questionText("updated text").answer("updated answer")
                .build();

        when(questionService.findById(anyLong())).thenReturn(null);

        MvcResult result = mockMvc.perform(put("/v1/question/{id}", questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateQuestion)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(0, responseBody.length());
    }
}