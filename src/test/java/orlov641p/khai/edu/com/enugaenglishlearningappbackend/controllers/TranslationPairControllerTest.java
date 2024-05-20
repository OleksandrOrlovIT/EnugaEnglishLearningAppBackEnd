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
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.CustomPageRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.TranslationPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.TranslationPairService;

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
class TranslationPairControllerTest {
    private static final long TRANSLATION_PAIR_ID = 1L;

    @Mock
    TranslationPairService translationPairService;

    @InjectMocks
    TranslationPairController translationPairController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(translationPairController).build();
    }

    @Test
    void retrieveTranslationPairs() throws Exception {
        when(translationPairService.findAll())
                .thenReturn(Arrays.asList(TranslationPair.builder().build(), TranslationPair.builder().build()));

        mockMvc.perform(get("/v1/translation-pairs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveTranslationPairById() throws Exception {
        String wordText = "WORD";

        EnglishWord englishWord = EnglishWord.builder()
                .id(TRANSLATION_PAIR_ID)
                .word(wordText)
                .build();

        UkrainianWord ukrainianWord = UkrainianWord.builder()
                .id(TRANSLATION_PAIR_ID)
                .word(wordText)
                .build();

        TranslationPair translationPair = TranslationPair.builder()
                .englishWord(englishWord)
                .ukrainianWord(ukrainianWord)
                .build();

        when(translationPairService.findById(TRANSLATION_PAIR_ID)).thenReturn(translationPair);

        MvcResult result = mockMvc.perform(get("/v1/translation-pair/{id}", TRANSLATION_PAIR_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        EntityModel<TranslationPair> responseModel = new ObjectMapper().readValue(responseBody, new TypeReference<>() {});

        assertEquals(translationPair.getUkrainianWord(), responseModel.getContent().getUkrainianWord());
        assertEquals(translationPair.getEnglishWord(), responseModel.getContent().getEnglishWord());
    }

    @Test
    void deleteTranslationPair() throws Exception {
        mockMvc.perform(delete("/v1/translation-pair/{id}", 1)).andExpect(status().is(204));
    }

    @Test
    void updateTranslationPair() throws Exception {
        UkrainianWord ukrainianWord1 = UkrainianWord.builder()
                .id(TRANSLATION_PAIR_ID)
                .build();

        UkrainianWord ukrainianWord2 = UkrainianWord.builder()
                .id(TRANSLATION_PAIR_ID + 1)
                .build();

        TranslationPair originalTranslationPair = TranslationPair.builder().id(TRANSLATION_PAIR_ID)
                .ukrainianWord(ukrainianWord1).build();

        TranslationPair updatedTranslationPair = TranslationPair.builder().id(TRANSLATION_PAIR_ID)
                .ukrainianWord(ukrainianWord2).build();

        when(translationPairService.findById(anyLong())).thenReturn(originalTranslationPair);
        when(translationPairService.update(any())).thenReturn(updatedTranslationPair);

        MvcResult result = mockMvc.perform(put("/v1/translation-pair/{id}", TRANSLATION_PAIR_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedTranslationPair)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        TranslationPair responseTranslationPair = new ObjectMapper().readValue(responseBody, TranslationPair.class);

        assertNotNull(responseTranslationPair);
        assertEquals(updatedTranslationPair, responseTranslationPair);
        assertEquals(updatedTranslationPair.getUkrainianWord().getId(), ukrainianWord2.getId());
    }

    @Test
    void createTranslationPair() throws Exception {
        TranslationPair translationPair = TranslationPair.builder().id(TRANSLATION_PAIR_ID).build();

        when(translationPairService.create(any())).thenReturn(translationPair);

        MvcResult result = mockMvc.perform(post("/v1/translation-pair")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(translationPair)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");

        assertNotNull(locationHeader);
        assertEquals("http://localhost/v1/translation-pair/1", locationHeader);
    }

    @Test
    void updateTranslationPair_findByIdNull() throws Exception {
        TranslationPair updatedTranslationPair = TranslationPair.builder().id(TRANSLATION_PAIR_ID).build();

        when(translationPairService.findById(anyLong())).thenReturn(null);

        MvcResult result = mockMvc.perform(put("/v1/translation-pair/{id}", TRANSLATION_PAIR_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedTranslationPair)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(0, responseBody.length());
    }

    @Test
    void retrieveTranslationPairsByPage() throws Exception {
        TranslationPair translationPair1 = TranslationPair.builder().id(TRANSLATION_PAIR_ID).build();
        TranslationPair translationPair2 = TranslationPair.builder().id(TRANSLATION_PAIR_ID + 1).build();

        Page<TranslationPair> mockPage =
                new PageImpl<>(
                        Arrays.asList(translationPair1, translationPair2),
                        PageRequest.of(0, 2), 2
                );

        when(translationPairService.findPageTranslationPairs(any(Pageable.class))).thenReturn(mockPage);

        CustomPageRequest customPageRequest = new CustomPageRequest(0, 2);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/v1/translation-pairs")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customPageRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(translationPair1.getId()))
                .andExpect(jsonPath("$.content[1].id").value(translationPair2.getId()))
                .andExpect(jsonPath("$.totalElements").value(2));
    }
}