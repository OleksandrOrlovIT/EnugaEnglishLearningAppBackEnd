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
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.EnglishTestService;

import java.util.Arrays;

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
public class EnglishTestControllerTest {
    private static final long englishTestId = 1L;

    @Mock
    EnglishTestService englishTestService;

    @InjectMocks
    EnglishTestController englishTestController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(englishTestController).build();
    }

    @Test
    void retrieveEnglishTests() throws Exception {
        when(englishTestService.findAll())
                .thenReturn(Arrays.asList(EnglishTest.builder().build(), EnglishTest.builder().build()));

        mockMvc.perform(get("/v1/english-tests")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveEnglishTestById() throws Exception {
        String englishTestName = "name";
        EnglishTest expectedEnglishTest = EnglishTest.builder()
                .id(englishTestId)
                .testName(englishTestName)
                .build();

        when(englishTestService.findById(englishTestId)).thenReturn(expectedEnglishTest);

        MvcResult result = mockMvc.perform(get("/v1/english-test/{id}", englishTestId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        EntityModel<EnglishTest> responseModel = new ObjectMapper().readValue(responseBody, new TypeReference<>() {});

        assertEquals(expectedEnglishTest, responseModel.getContent());
    }

    @Test
    void deleteEnglishTest() throws Exception {
        mockMvc.perform(delete("/v1/english-test/{id}", 1)).andExpect(status().is(204));
    }

    @Test
    void updateEnglishTest() throws Exception {
        EnglishTest originalEnglishTest = EnglishTest.builder().id(englishTestId).testName("name").build();
        EnglishTest updatedEnglishTest = EnglishTest.builder().id(englishTestId).testName("updated name").build();

        when(englishTestService.findById(anyLong())).thenReturn(originalEnglishTest);
        when(englishTestService.update(any())).thenReturn(updatedEnglishTest);

        MvcResult result = mockMvc.perform(put("/v1/english-test/{id}", englishTestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedEnglishTest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        EnglishTest responseEnglishTest = new ObjectMapper().readValue(responseBody, EnglishTest.class);

        assertNotNull(responseEnglishTest);
        assertEquals(updatedEnglishTest, responseEnglishTest);
    }

    @Test
        void createEnglishTest() throws Exception {
        EnglishTest inputEnglishTest = EnglishTest.builder().id(englishTestId).build();

        when(englishTestService.create(any())).thenReturn(inputEnglishTest);

        MvcResult result = mockMvc.perform(post("/v1/english-test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputEnglishTest)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");

        assertNotNull(locationHeader);
        assertEquals("http://localhost/v1/english-test/1", locationHeader);
    }

    @Test
    void updateEnglishTest_findByIdNull() throws Exception {
        EnglishTest updatedEnglishTest = EnglishTest.builder().id(englishTestId).testName("updated name").build();

        when(englishTestService.findById(anyLong())).thenReturn(null);

        MvcResult result = mockMvc.perform(put("/v1/english-test/{id}", englishTestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedEnglishTest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(0, responseBody.length());
    }
}
