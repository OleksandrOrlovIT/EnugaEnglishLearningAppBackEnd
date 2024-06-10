package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.vocabulary;

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
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.request.CustomPageRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary.UkrainianWordService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UkrainianWordControllerTest {
    private static final long UKRAINIAN_WORD_ID = 1L;

    @Mock
    UkrainianWordService ukrainianWordService;

    @InjectMocks
    UkrainianWordController ukrainianWordController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ukrainianWordController).build();
    }

    @Test
    void retrieveUkrainianWords() throws Exception {
        when(ukrainianWordService.findAll())
                .thenReturn(Arrays.asList(UkrainianWord.builder().build(), UkrainianWord.builder().build()));

        mockMvc.perform(get("/v1/ukr-words")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveUkrainianWordById() throws Exception {
        String ukrainianWordText = "WORD";
        UkrainianWord ukrainianWord = UkrainianWord.builder()
                .id(UKRAINIAN_WORD_ID)
                .word(ukrainianWordText)
                .build();

        when(ukrainianWordService.findById(UKRAINIAN_WORD_ID)).thenReturn(ukrainianWord);

        MvcResult result = mockMvc.perform(get("/v1/ukr-word/{id}", UKRAINIAN_WORD_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        EntityModel<UkrainianWord> responseModel = new ObjectMapper().readValue(responseBody, new TypeReference<>() {});

        assertEquals(ukrainianWord, responseModel.getContent());
    }

    @Test
    void deleteUkrainianWord() throws Exception {
        mockMvc.perform(delete("/v1/ukr-word/{id}", 1)).andExpect(status().is(204));
    }

    @Test
    void updateUkrainianWord() throws Exception {
        UkrainianWord originalUkrainianWord = UkrainianWord.builder().id(UKRAINIAN_WORD_ID).word("name").build();
        UkrainianWord updatedUkrainianWord = UkrainianWord.builder().id(UKRAINIAN_WORD_ID).word("updated name").build();

        when(ukrainianWordService.findById(anyLong())).thenReturn(originalUkrainianWord);
        when(ukrainianWordService.update(any())).thenReturn(updatedUkrainianWord);

        MvcResult result = mockMvc.perform(put("/v1/ukr-word/{id}", UKRAINIAN_WORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedUkrainianWord)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        UkrainianWord responseUkrainianWord = new ObjectMapper().readValue(responseBody, UkrainianWord.class);

        assertNotNull(responseUkrainianWord);
        assertEquals(updatedUkrainianWord, responseUkrainianWord);
    }

    @Test
    void createUkrainianWord() throws Exception {
        UkrainianWord ukrainianWord = UkrainianWord.builder().id(UKRAINIAN_WORD_ID).build();

        when(ukrainianWordService.create(any())).thenReturn(ukrainianWord);

        MvcResult result = mockMvc.perform(post("/v1/ukr-word")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ukrainianWord)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");

        assertNotNull(locationHeader);
        assertEquals("http://localhost/v1/ukr-word/1", locationHeader);
    }

    @Test
    void updateUkrainianWord_findByIdNull() throws Exception {
        UkrainianWord updatedUkrainianWord = UkrainianWord.builder().id(UKRAINIAN_WORD_ID).word("updated name").build();

        when(ukrainianWordService.findById(anyLong())).thenReturn(null);

        MvcResult result = mockMvc.perform(put("/v1/ukr-word/{id}", UKRAINIAN_WORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedUkrainianWord)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(0, responseBody.length());
    }

    @Test
    void retrieveUkrainianWordsByPage() throws Exception {
        UkrainianWord word1 = UkrainianWord.builder().id(UKRAINIAN_WORD_ID).word("name1").build();
        UkrainianWord word2 = UkrainianWord.builder().id(UKRAINIAN_WORD_ID + 1).word("name2").build();

        Page<UkrainianWord> mockPage =
                new PageImpl<>(Arrays.asList(word1, word2), PageRequest.of(0, 2), 2);

        when(ukrainianWordService.findPageUkrainianWords(any(Pageable.class))).thenReturn(mockPage);

        CustomPageRequest customPageRequest = new CustomPageRequest(0, 2);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/v1/ukr-words")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customPageRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].word").value(word1.getWord()))
                .andExpect(jsonPath("$.content[1].word").value(word2.getWord()))
                .andExpect(jsonPath("$.totalElements").value(2));
    }
}