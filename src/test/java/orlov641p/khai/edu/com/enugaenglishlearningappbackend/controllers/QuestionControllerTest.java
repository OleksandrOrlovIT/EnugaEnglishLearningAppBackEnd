package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers;

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
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.QuestionService;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .thenReturn(Arrays.asList(Question.builder().id(1L).build(), Question.builder().id(2L).build()));

        mockMvc.perform(get("/v1/questions")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveRuleById() throws Exception {
        String questionText = "Some text", answer = "answer";
        Question expectedQuestion = Question.builder().id(questionId).questionText(questionText).answer(answer).build();

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
        Question originalQuestion = Question.builder().id(questionId).questionText("some text").answer("answer").build();
        Question updateQuestion = Question.builder().id(questionId).questionText("updated text").answer("updated answer").build();

        when(questionService.findById(anyLong())).thenReturn(originalQuestion);
        when(questionService.save(any())).thenReturn(updateQuestion);

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
        Question inputQuestion = Question.builder().id(null).questionText("some text").build();
        Question savedQuestion = Question.builder().id(questionId).questionText("some text").build();

        when(questionService.save(any())).thenReturn(savedQuestion);

        MvcResult result = mockMvc.perform(post("/v1/question")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(inputQuestion)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");

        assertNotNull(locationHeader);
        assertEquals("http://localhost/v1/question/1", locationHeader);
    }
}