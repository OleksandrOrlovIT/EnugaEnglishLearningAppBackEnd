package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book;

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
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.response.PageResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Page;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.PageService;

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
class PageControllerTest {
    private static final long PAGE_ID = 1L;

    @Mock
    PageService pageService;

    @InjectMocks
    PageController pageController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pageController).build();
    }

    @Test
    void retrievePages() throws Exception {
        when(pageService.findAll())
                .thenReturn(Arrays.asList(new Page(), new Page()));

        mockMvc.perform(get("/v1/pages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrievePageById() throws Exception {
        String content = "TITLE";
        Page page = Page.builder()
                .id(PAGE_ID)
                .content(content)
                .build();

        when(pageService.findById(PAGE_ID)).thenReturn(page);

        MvcResult result = mockMvc.perform(get("/v1/page/{id}", PAGE_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        EntityModel<Page> responseModel = new ObjectMapper().readValue(responseBody, new TypeReference<>() {});

        assertEquals(page, responseModel.getContent());
    }

    @Test
    void deletePage() throws Exception {
        mockMvc.perform(delete("/v1/page/{id}", 1)).andExpect(status().is(204));
    }

    @Test
    void updatePage() throws Exception {
        Page originalPage = Page.builder().id(PAGE_ID).content("content").build();
        Page updatedPage = Page.builder().id(PAGE_ID).content("updated content").build();

        when(pageService.findById(anyLong())).thenReturn(originalPage);
        when(pageService.update(any())).thenReturn(updatedPage);

        MvcResult result = mockMvc.perform(put("/v1/page/{id}", PAGE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedPage)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PageResponse pageResponse = new ObjectMapper().readValue(responseBody, PageResponse.class);

        assertNotNull(pageResponse);
        assertEquals(updatedPage.getContent(), pageResponse.getContent());
    }

    @Test
    void createPage() throws Exception {
        Page page = Page.builder().id(PAGE_ID).build();

        when(pageService.create(any())).thenReturn(page);

        MvcResult result = mockMvc.perform(post("/v1/page")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(page)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");

        assertNotNull(locationHeader);
        assertEquals("http://localhost/v1/page/1", locationHeader);
    }

    @Test
    void updatePage_findByIdNull() throws Exception {
        Page updatedPage = Page.builder().id(PAGE_ID).content("updated content").build();

        when(pageService.findById(anyLong())).thenReturn(null);

        MvcResult result = mockMvc.perform(put("/v1/page/{id}", PAGE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedPage)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(0, responseBody.length());
    }
}