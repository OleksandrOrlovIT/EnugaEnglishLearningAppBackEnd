package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.request.CustomPageRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.EnglishWordService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EnglishWordControllerTest {
    private static final long ENGLISH_WORD_ID = 1L;

    @Mock
    EnglishWordService englishWordService;

    @InjectMocks
    EnglishWordController englishWordController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(englishWordController).build();
    }

    @Test
    void retrieveEnglishWords() throws Exception {
        when(englishWordService.findAll())
                .thenReturn(Arrays.asList(EnglishWord.builder().build(), EnglishWord.builder().build()));

        mockMvc.perform(get("/v1/eng-words")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveEnglishWordById() throws Exception {
        String englishWordText = "WORD";
        EnglishWord englishWord = EnglishWord.builder()
                .id(ENGLISH_WORD_ID)
                .word(englishWordText)
                .build();

        when(englishWordService.findById(ENGLISH_WORD_ID)).thenReturn(englishWord);

        MvcResult result = mockMvc.perform(get("/v1/eng-word/{id}", ENGLISH_WORD_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        EntityModel<EnglishWord> responseModel = new ObjectMapper().readValue(responseBody, new TypeReference<>() {});

        assertEquals(englishWord, responseModel.getContent());
    }

    @Test
    void deleteEnglishWord() throws Exception {
        mockMvc.perform(delete("/v1/eng-word/{id}", 1)).andExpect(status().is(204));
    }

    @Test
    void updateEnglishWord() throws Exception {
        EnglishWord originalEnglishWord = EnglishWord.builder().id(ENGLISH_WORD_ID).word("name").build();
        EnglishWord updatedEnglishWord = EnglishWord.builder().id(ENGLISH_WORD_ID).word("updated name").build();

        when(englishWordService.findById(anyLong())).thenReturn(originalEnglishWord);
        when(englishWordService.update(any())).thenReturn(updatedEnglishWord);

        MvcResult result = mockMvc.perform(put("/v1/eng-word/{id}", ENGLISH_WORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedEnglishWord)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        EnglishWord responseEnglishWord = new ObjectMapper().readValue(responseBody, EnglishWord.class);

        assertNotNull(responseEnglishWord);
        assertEquals(updatedEnglishWord, responseEnglishWord);
    }

    @Test
    void createEnglishWord() throws Exception {
        EnglishWord englishWord = EnglishWord.builder().id(ENGLISH_WORD_ID).build();

        when(englishWordService.create(any())).thenReturn(englishWord);

        MvcResult result = mockMvc.perform(post("/v1/eng-word")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(englishWord)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");

        assertNotNull(locationHeader);
        assertEquals("http://localhost/v1/eng-word/1", locationHeader);
    }

    @Test
    void updateEnglishWord_findByIdNull() throws Exception {
        EnglishWord updatedEnglishWord = EnglishWord.builder().id(ENGLISH_WORD_ID).word("updated name").build();

        when(englishWordService.findById(anyLong())).thenReturn(null);

        MvcResult result = mockMvc.perform(put("/v1/eng-word/{id}", ENGLISH_WORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedEnglishWord)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(0, responseBody.length());
    }

    @Test
    void retrieveEnglishWordsByPage() throws Exception {
        EnglishWord word1 = EnglishWord.builder().id(ENGLISH_WORD_ID).word("name1").build();
        EnglishWord word2 = EnglishWord.builder().id(ENGLISH_WORD_ID + 1).word("name2").build();

        Page<EnglishWord> mockPage =
                new PageImpl<>(Arrays.asList(word1, word2), PageRequest.of(0, 2), 2);

        when(englishWordService.findPageEnglishWords(any(Pageable.class))).thenReturn(mockPage);

        CustomPageRequest customPageRequest = new CustomPageRequest(0, 2);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/v1/eng-words")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customPageRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].word").value(word1.getWord()))
                .andExpect(jsonPath("$.content[1].word").value(word2.getWord()))
                .andExpect(jsonPath("$.totalElements").value(2));
    }
}