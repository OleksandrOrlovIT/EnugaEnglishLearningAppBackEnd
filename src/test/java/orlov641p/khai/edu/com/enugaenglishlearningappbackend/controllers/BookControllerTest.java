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
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.response.BookResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Book;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.BookService;

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
class BookControllerTest {
    private static final long BOOK_ID = 1L;

    @Mock
    BookService bookService;

    @InjectMocks
    BookController bookController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void retrieveBooks() throws Exception {
        when(bookService.findAll())
                .thenReturn(Arrays.asList(new Book(), new Book()));

        mockMvc.perform(get("/v1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void retrieveBookById() throws Exception {
        String bookTitle = "TITLE";
        Book book = Book.builder()
                .id(BOOK_ID)
                .title(bookTitle)
                .build();

        when(bookService.findById(BOOK_ID)).thenReturn(book);

        MvcResult result = mockMvc.perform(get("/v1/book/{id}", BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        EntityModel<Book> responseModel = new ObjectMapper().readValue(responseBody, new TypeReference<>() {});

        assertEquals(book, responseModel.getContent());
    }

    @Test
    void deleteBook() throws Exception {
        mockMvc.perform(delete("/v1/book/{id}", 1)).andExpect(status().is(204));
    }

    @Test
    void updateBook() throws Exception {
        Book originalBook = Book.builder().id(BOOK_ID).title("title").build();
        Book updatedBook = Book.builder().id(BOOK_ID).title("updated title").build();

        when(bookService.findById(anyLong())).thenReturn(originalBook);
        when(bookService.update(any())).thenReturn(updatedBook);

        MvcResult result = mockMvc.perform(put("/v1/book/{id}", BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        BookResponse bookResponse = new ObjectMapper().readValue(responseBody, BookResponse.class);

        assertNotNull(bookResponse);
        assertEquals(updatedBook.getTitle(), bookResponse.getTitle());
    }

    @Test
    void createBook() throws Exception {
        Book book = Book.builder().id(BOOK_ID).build();

        when(bookService.create(any())).thenReturn(book);

        MvcResult result = mockMvc.perform(post("/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");

        assertNotNull(locationHeader);
        assertEquals("http://localhost/v1/book/1", locationHeader);
    }

    @Test
    void updateBook_findByIdNull() throws Exception {
        Book updatedBook = Book.builder().id(BOOK_ID).title("updated book").build();

        when(bookService.findById(anyLong())).thenReturn(null);

        MvcResult result = mockMvc.perform(put("/v1/book/{id}", BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(0, responseBody.length());
    }
}